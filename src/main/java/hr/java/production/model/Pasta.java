package hr.java.production.model;

import hr.java.production.main.Main;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Pasta extends Item implements Edible {
    private static final int CAL_PER_KG = 100;

    private BigDecimal weight;

    public Pasta(String name, Category category, BigDecimal width, BigDecimal height,
                 BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice);
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
        return getSellingPrice().multiply(weight);
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
}
