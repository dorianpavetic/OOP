package hr.java.production.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Laptop extends Item implements Technical {
    private final BigInteger warrantyDuration;

    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                  BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigInteger warrantyDuration) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyDuration = warrantyDuration;
    }

    @Override
    public BigInteger getWarrantyDuration() {
        return warrantyDuration;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(Laptop.class.getSimpleName())
                .append(" ")
                .append(super.toString())
                .append(". Warranty duration: ")
                .append(getWarrantyDuration())
                .append(" months")
                .toString();
    }
}
