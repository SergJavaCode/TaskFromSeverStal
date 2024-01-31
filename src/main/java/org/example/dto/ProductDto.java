package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.product.Product;
import org.example.model.supply.SupplyReport;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String type;
    private String sort;
    private String description;

    public ProductDto(Product model) {
        this.id = model.getId();
        this.type = model.getType().getName();
        this.sort = model.getSort().getName();
        this.description = model.getDescription();
    }

    public ProductDto(SupplyReport sqlResult) {
        this.id = sqlResult.getProductId();
        this.type = sqlResult.getProductType();
        this.sort = sqlResult.getProductSort();
        this.description = sqlResult.getProductDescription();
    }
}
