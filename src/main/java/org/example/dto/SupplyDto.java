package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.supply.Supply;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SupplyDto {
    private Long id;
    private SupplierDto supplier;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<SupplyUnitDto> units;

    public SupplyDto(Supply model) {
        this.id = model.getId();
        this.supplier = new SupplierDto(model.getSupplier());
        this.created = model.getSupplyCreated();
        this.updated = model.getSupplyUpdated();
        this.units = model.getUnits()
                .stream()
                .map(SupplyUnitDto::new)
                .collect(Collectors.toList());
    }
}
