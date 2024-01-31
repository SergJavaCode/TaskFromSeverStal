package org.example.model.supply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Supplier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "supply")
@Setter
@Getter
@NoArgsConstructor
public class Supply {
    public Supply(Supplier supplier) {
        this.supplier = supplier;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @CreationTimestamp
    private LocalDateTime supplyCreated;

    @UpdateTimestamp
    private LocalDateTime supplyUpdated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supply", cascade = CascadeType.REMOVE)
    private Set<SupplyUnit> units = new HashSet<>();
}
