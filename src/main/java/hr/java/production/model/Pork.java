package hr.java.production.model;

import hr.java.production.exception.NegativeNumberOperationException;
import hr.java.production.main.Main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Edible item to be sold or produced. Has all properties of {@link Item}
 * Additionally, contains weight needed for calculating kilocalories and price.
 */
public class Pork extends Item implements Edible {
    private static final int CAL_PER_KG = -300;

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
    public Pork(String name, Category category, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
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
        BigInteger kiloCalories = getWeight().multiply(BigDecimal.valueOf(CAL_PER_KG)).toBigInteger();
        if(kiloCalories.intValue() < 0)
            throw new NegativeNumberOperationException("Kilocalories must be positive");
        return kiloCalories;
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal discount = getDiscount().discountAmount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
        BigDecimal price = getSellingPrice().multiply(weight).multiply(new BigDecimal(1).subtract(discount));
        if(price.doubleValue() < 0)
            throw new NegativeNumberOperationException("Price must be positive");
        return price;
    }

    @Override
    public String toString() {
        BigInteger kilocalories;
        BigDecimal price;
        try {
            kilocalories = calculateKilocalories();
        } catch (NegativeNumberOperationException ex) {
            kilocalories = BigInteger.ZERO;
        }

        try {
            price = calculatePrice();
        } catch (NegativeNumberOperationException ex) {
            price = BigDecimal.ZERO;
        }

        return new StringBuilder(Pork.class.getSimpleName())
                .append(" ")
                .append(getName())
                .append(" ")
                .append(weight)
                .append("kg (")
                .append(getCategory().toString())
                .append(". Calories: ")
                .append(kilocalories)
                .append(" kcal")
                .append(". Price: ")
                .append(price)
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
}
