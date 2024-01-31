package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Supplier;
import org.example.model.supply.SupplyReport;

@Getter
@Setter
@NoArgsConstructor
public class SupplierDto {
    private Integer id;
    private String name;
    private Long inn;
    private Long phone;
    private String address;

    public SupplierDto(Supplier model) {
        this.id = model.getId();
        this.name = model.getName();
        this.inn = model.getInn();
        this.phone = model.getPhone();
        this.address = model.getAddress();
    }

    public SupplierDto(SupplyReport sqlResult) {
        this.id = sqlResult.getSupplierId();
        this.name = sqlResult.getSupplierName();
        this.inn = sqlResult.getSupplierInn();
        this.phone = sqlResult.getSupplierPhone();
        this.address = sqlResult.getSupplierAddress();
    }
}
