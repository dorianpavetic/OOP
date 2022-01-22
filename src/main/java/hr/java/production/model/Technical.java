package hr.java.production.model;

import java.math.BigInteger;

public sealed interface Technical permits Laptop {
    /**
     * Returns warranty duration for technical item.
     *
     * @return warranty duration for technical item.
     */
    BigInteger getWarrantyDuration();
}
