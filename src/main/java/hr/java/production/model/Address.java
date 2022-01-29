package hr.java.production.model;

import hr.java.production.enums.City;

import java.util.Objects;

/**
 * Represents address of factory, store or any other entity that can be located by address.
 * Instantiated by builder pattern rather than by constructor.
 */
public class Address {
    private String street;
    private String houseNumber;
    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return Address.class.getSimpleName()
                .concat(": ")
                .concat(street)
                .concat(", ")
                .concat(houseNumber)
                .concat(", ")
                .concat(city.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(houseNumber, address.houseNumber) && city == address.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city);
    }

    /**
     * Builder pattern class for creating {@link Address}.
     */
    public static class Builder {
        private String street;
        private String houseNumber;
        private City city;

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
         * Sets new city in builder, including name and postal code.
         *
         * @param city new city with name and postal code.
         * @return builder object that can take more value parameters.
         */
        public Builder city(City city) {
            this.city = city;
            return this;
        }
    }
}
