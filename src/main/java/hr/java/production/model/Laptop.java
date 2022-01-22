package hr.java.production.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Laptop extends Item implements Technical {
    private final BigInteger warrantyDuration;
    private final Package packaging;

    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                  BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigInteger warrantyDuration, Package packaging) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyDuration = warrantyDuration;
        this.packaging = packaging;
    }

    @Override
    public BigInteger getWarrantyDuration() {
        return warrantyDuration;
    }

    public Package getPackaging() {
        return packaging;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(Laptop.class.getSimpleName())
                .append(" ")
                .append(super.toString())
                .append(" packaged in '")
                .append(packaging.name())
                .append("'")
                .append(". Warranty duration: ")
                .append(getWarrantyDuration())
                .append(" months")
                .toString();
    }
}
