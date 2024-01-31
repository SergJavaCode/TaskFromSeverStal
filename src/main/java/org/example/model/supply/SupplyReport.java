package org.example.model.supply;

public interface SupplyReport {
    Long getProductId();

    String getProductDescription();

    String getProductType();

    String getProductSort();

    Integer getSupplierId();

    String getSupplierName();

    Long getSupplierInn();

    String getSupplierAddress();

    Long getSupplierPhone();

    Long getTotalQuantity();

    Long getTotalSum();
}
