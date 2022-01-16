package hr.java.production.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Edible {
    BigInteger calculateKilocalories();
    BigDecimal calculatePrice();
}
