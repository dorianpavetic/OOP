package hr.java.production.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Representing items that can be eaten.
 */
public interface Edible {
    /**
     * Calculates kilocalories for item based on weight.
     *
     * @return calculated kilocalories.
     */
    BigInteger calculateKilocalories();

    /**
     * Calculates price for item based on weight.
     *
     * @return calculated priced.
     */
    BigDecimal calculatePrice();
}
