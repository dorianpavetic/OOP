package hr.java.production.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Technical item that represents laptop and has warranty.
 */
public final class Laptop extends Item implements Technical {
    private final BigInteger warrantyDuration;

    /**
     * Creates new technical item with all fields.
     *
     * @param name name of laptop.
     * @param category selected category of laptop.
     * @param width width of laptop in inches.
     * @param height height of laptop in inches.
     * @param length length of laptop in inches.
     * @param productionCost amount laptop costs to make.
     * @param sellingPrice amount laptop is selling for.
     * @param discount discount data about laptop.
     * @param warrantyDuration warranty duration of laptop in months.
     */
    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                  BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigInteger warrantyDuration) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyDuration = warrantyDuration;
    }

    /**
     * Returns warranty duration for technical item.
     *
     * @return warranty duration for technical item.
     */
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
