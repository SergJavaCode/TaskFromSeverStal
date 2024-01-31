package org.example.repository;

import org.example.model.supply.Supply;
import org.example.model.supply.SupplyReport;
import org.example.model.supply.SupplyWithSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query(nativeQuery = true,
            value = "SELECT p.id                      AS productId,"
                    + "       p.description               AS productDescription,"
                    + "       pt.name                     AS productType,"
                    + "       pc.name                     AS productSort,"
                    + "       spr.id                      AS supplierId,"
                    + "       spr.name                    AS supplierName,"
                    + "       spr.inn                     AS supplierInn,"
                    + "       spr.address                 AS supplierAddress,"
                    + "       spr.phone                   AS supplierPhone,"
                    + "       sum(su.quantity)            AS totalQuantity,"
                    + "       sum(su.price * su.quantity) AS totalSum"
                    + " FROM supply s"
                    + "         LEFT JOIN supplier spr ON s.supplier_id = spr.id"
                    + "         LEFT JOIN supply_unit su ON s.id = su.supply_id"
                    + "         LEFT JOIN product p ON p.id = su.product_id"
                    + "         LEFT JOIN product_sort pc ON p.sort_id = pc.id"
                    + "         LEFT JOIN product_type pt ON p.type_id = pt.id"
                    + " WHERE s.supply_created BETWEEN :start AND :end"
                    + " GROUP BY p.id, pc.id, pt.id, spr.id"
                    + " ORDER BY p.id ASC, spr.id asc")
    List<SupplyReport> getReportByPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query(nativeQuery = true,
            value = "SELECT s.id                      AS id,"
                    + "       s.created                   AS created,"
                    + "       spr.name                    AS supplier,"
                    + "       sum(su.price * su.quantity) AS sum"
                    + " FROM supply s"
                    + "         LEFT JOIN supplier spr ON s.supplier_id = spr.id"
                    + "         LEFT JOIN supply_unit su ON s.id = su.supply_id"
                    + " GROUP BY s.id, spr.name, s.created"
                    + " ORDER BY s.created desc"
                    + " LIMIT :amount")
    List<SupplyWithSum> getLastSupplyWithSum(@Param("amount") Integer amount);
}
