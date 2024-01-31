package org.example.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_sort")
@Getter
@Setter
@NoArgsConstructor
public class ProductSort {
    @Id
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private ProductType productType;
}
