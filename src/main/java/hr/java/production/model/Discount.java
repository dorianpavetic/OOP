package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Record representing discount in percentage.
 */
public record Discount(BigDecimal discountAmount) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(discountAmount, discount.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }
}