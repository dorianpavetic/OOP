package hr.java.production.model;

import java.math.BigInteger;

public sealed interface Technical permits Laptop {
    BigInteger getWarrantyDuration();
}
