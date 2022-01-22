package hr.java.production.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class Apple extends JuiceEntity implements Squeezable {
    private static final double JUICE_AMOUNT_PER_KG = 0.2d; //l

    public Apple(String name, LocalDateTime createDateTime, Item[] items, BigDecimal weight) {
        super(name, createDateTime, items, weight);
    }

    @Override
    public BigDecimal getSqueezeJuiceAmount() {
        return getWeight().multiply(BigDecimal.valueOf(JUICE_AMOUNT_PER_KG));
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("Amount of juice it can produce: ")
                .append(getSqueezeJuiceAmount())
                .append(" l")
                .toString();
    }
}
