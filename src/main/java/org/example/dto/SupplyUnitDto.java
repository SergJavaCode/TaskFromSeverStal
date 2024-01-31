package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.supply.SupplyUnit;

@Getter
@Setter
@NoArgsConstructor
public class SupplyUnitDto {
    private Long id;
    private ProductDto productDto;
    private Integer quantity;
    private Double price;
    private String weightMeasure;

    public SupplyUnitDto(SupplyUnit model) {
        this.id = model.getId();
        this.productDto = new ProductDto(model.getProduct());
        this.quantity = model.getQuantity();
        this.price = model.getPrice();
        this.weightMeasure = model.getMeasure().toString();
    }
}
