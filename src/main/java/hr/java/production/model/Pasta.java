package hr.java.production.model;

import hr.java.production.main.Main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Edible item to be sold or produced. Has all properties of {@link Item}
 * Additionally, contains weight needed for calculating kilocalories and price.
 */
public class Pasta extends Item implements Edible {
    private static final int CAL_PER_KG = 100;

    private BigDecimal weight;

    /**
     * Creates new edible item with all fields.
     * Width, length and height are ZERO.
     *
     * @param name name of edible item.
     * @param category selected category of edible item.
     * @param productionCost amount edible item costs to make.
     * @param sellingPrice amount edible item is selling for.
     * @param discount discount data about edible item.
     * @param weight weight of edible item.
     */
    public Pasta(String name, Category category, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(name, category, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public BigInteger calculateKilocalories() {
        return getWeight().multiply(BigDecimal.valueOf(CAL_PER_KG)).toBigInteger();
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal discount = getDiscount().discountAmount().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
        return getSellingPrice().multiply(weight).multiply(new BigDecimal(1).subtract(discount));
    }

    @Override
    public String toString() {
        return new StringBuilder(Pasta.class.getSimpleName())
                .append(" ")
                .append(getName())
                .append(" ")
                .append(weight)
                .append("kg (")
                .append(getCategory().toString())
                .append(". Calories: ")
                .append(calculateKilocalories())
                .append(" kcal")
                .append(". Price: ")
                .append(calculatePrice())
                .append(" ")
                .append(Main.CURRENCY_UNIT)
                .append(" (selling price: ")
                .append(getSellingPrice())
                .append(")")
                .append(" (prod. cost: ")
                .append(getProductionCost())
                .append(")")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pasta pasta = (Pasta) o;
        return Objects.equals(weight, pasta.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}
