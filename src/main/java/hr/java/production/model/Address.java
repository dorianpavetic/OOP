package hr.java.production.model;

public class Address {
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

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

    public static class Builder {
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;

        public Builder() {
        }

        public Address build() {
            Address address = new Address();
            address.street = street;
            address.houseNumber = houseNumber;
            address.city = city;
            address.postalCode = postalCode;

            return address;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }
    }
}
