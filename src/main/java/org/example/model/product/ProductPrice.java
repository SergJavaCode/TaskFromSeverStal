package org.example.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Measure;
import org.example.model.Supplier;
import org.example.util.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "product_price")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Setter
@Getter
@NoArgsConstructor
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @Column(name = "period_beginning")
    private LocalDate periodBeginning;

    @Column(name = "period_end")
    private LocalDate periodEnd;

    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "measure")
    @Type(type = "pgsql_enum")
    private Measure measure;
}
