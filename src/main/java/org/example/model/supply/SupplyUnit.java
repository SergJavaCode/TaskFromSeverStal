package org.example.model.supply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Measure;
import org.example.model.product.Product;
import org.example.model.product.ProductPrice;
import org.example.util.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "supply_unit")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@NoArgsConstructor
public class SupplyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supply_id", referencedColumnName = "id")
    private Supply supply;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private Integer quantity;

    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "measure")
    @Type(type = "pgsql_enum")
    private Measure measure;

    public SupplyUnit(@NotNull ProductPrice productPrice, Integer quantity, Supply supply) {
        this.product = productPrice.getProduct();
        this.price = productPrice.getPrice();
        this.measure = productPrice.getMeasure();
        this.quantity = quantity;
        this.supply = supply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplyUnit that = (SupplyUnit) o;
        return Objects.equals(supply, that.supply)
                && Objects.equals(product, that.product)
                && Objects.equals(quantity, that.quantity)
                && Objects.equals(price, that.price)
                && measure == that.measure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supply, product, quantity, price, measure);
    }
}
