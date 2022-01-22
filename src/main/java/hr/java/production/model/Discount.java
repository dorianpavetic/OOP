package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Record representing discount in percentage.
 */
public record Discount(BigDecimal discountAmount) {
}