package hr.java.production.model;

/**
 * Represents address of factory, store or any other entity that can be located by address.
 * Instantiated by builder pattern rather than by constructor.
 */
public class Address {
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

    /**
     * Empty constructor intended to be called by nested builder.
     */
    private Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return Address.class.getSimpleName()
                .concat(": ")
                .concat(street)
                .concat(", ")
                .concat(houseNumber)
                .concat(", ")
                .concat(city)
                .concat(", ")
                .concat(postalCode);
    }

    /**
     * Builder pattern class for creating {@link Address}.
     */
    public static class Builder {
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;

        /**
         * Creates builder object.
         */
        public Builder() {
        }

        /**
         * Creates new Address object with only the provided fields.
         *
         * @return new {@link Address} with all fields optional
         */
        public Address build() {
            Address address = new Address();
            address.street = street;
            address.houseNumber = houseNumber;
            address.city = city;
            address.postalCode = postalCode;

            return address;
        }

        /**
         * Sets new street name in builder.
         *
         * @param street new street name.
         * @return builder object that can take more value parameters.
         */
        public Builder street(String street) {
            this.street = street;
            return this;
        }

        /**
         * Sets new house number in builder.
         *
         * @param houseNumber new house number.
         * @return builder object that can take more value parameters.
         */
        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * Sets new city name in builder.
         *
         * @param city new city name.
         * @return builder object that can take more value parameters.
         */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /**
         * Sets new postal code in builder.
         *
         * @param postalCode new address street.
         * @return builder object that can take more value parameters.
         */
        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }
    }
}
