package org.example.service;

import org.example.dto.SupplyCreateDto;
import org.example.model.Supplier;
import org.example.model.supply.Supply;
import org.example.model.supply.SupplyReport;
import org.example.model.supply.SupplyWithSum;

import java.time.LocalDate;
import java.util.List;

public interface SupplyService {
    Supply createSupply(SupplyCreateDto req);

    List<SupplyReport> getReportByPeriod(LocalDate from, LocalDate to);

    List<Supplier> getAllSuppliers();

    List<SupplyWithSum> getLastSupplyWithSum(Integer amount);
}
