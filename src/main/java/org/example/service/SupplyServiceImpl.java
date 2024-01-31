package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.SupplyCreateDto;
import org.example.dto.SupplyUnitCreateDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.model.Supplier;
import org.example.model.product.ProductPrice;
import org.example.model.supply.Supply;
import org.example.model.supply.SupplyReport;
import org.example.model.supply.SupplyUnit;
import org.example.model.supply.SupplyWithSum;
import org.example.repository.ProductRepository;
import org.example.repository.SupplierRepository;
import org.example.repository.SupplyRepository;
import org.example.repository.SupplyUnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class SupplyServiceImpl implements SupplyService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final SupplyRepository supplyRepository;
    private final SupplyUnitRepository supplyUnitRepository;

    @Override
    @Transactional
    public Supply createSupply(SupplyCreateDto req) {
        if (isNull(req.getUnits()) || req.getUnits().isEmpty()) {
            throw new ValidationException("Units is empty!");
        }
        if (isNull(req.getSupplierId())) {
            throw new ValidationException("SupplierId is empty!");
        }
        Supplier supplier = supplierRepository.findById(req.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        List<ProductPrice> prices = productRepository.getProductPrices(
                req.getSupplierId(),
                req.getUnits()
                        .stream()
                        .map(SupplyUnitCreateDto::getProductId)
                        .collect(Collectors.toList()),
                LocalDate.now()
        );

        Supply result = new Supply(supplier);
        supplyRepository.saveAndFlush(result);
        Set<SupplyUnit> supplyUnit = unionEqualsProducts(req.getUnits()).entrySet().stream()
                .map(en -> {
                    ProductPrice productPrice = prices.stream()
                            .filter(price -> Objects.equals(en.getKey(), price.getProduct().getId()))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Price for productId " + en.getKey() + " not found"));
                    return new SupplyUnit(productPrice, en.getValue(), result);
                })
                .collect(Collectors.toSet());
        supplyUnitRepository.saveAllAndFlush(supplyUnit);
        result.setUnits(supplyUnit);
        return result;
    }

    private Map<Long, Integer> unionEqualsProducts(List<SupplyUnitCreateDto> units) {
        Map<Long, Integer> result = new HashMap<>();

        units.forEach(un -> {
            if (un.getQuantity() <= 0) {
                throw new ValidationException("Quantity < 0");
            }
            if (result.containsKey(un.getProductId())) {
                Integer oldQuantity = result.get(un.getProductId());
                result.put(un.getProductId(), oldQuantity + un.getQuantity());
            } else {
                result.put(un.getProductId(), un.getQuantity());
            }
        });

        return result;
    }

    @Override
    public List<SupplyReport> getReportByPeriod(LocalDate from, LocalDate to) {
        return supplyRepository.getReportByPeriod(from.atStartOfDay(), to.atStartOfDay().plusHours(24));
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public List<SupplyWithSum> getLastSupplyWithSum(Integer amount) {
        return supplyRepository.getLastSupplyWithSum(amount);
    }
}
