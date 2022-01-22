package hr.java.production.model;

import java.math.BigDecimal;

public sealed interface Squeezable permits Apple, Orange {
    BigDecimal getSqueezeJuiceAmount();
}
