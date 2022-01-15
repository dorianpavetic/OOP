package hr.java.production.model;

public class Address extends Printable {
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

    public Address(String street, String houseNumber, String city, String postalCode, Integer index) {
        super(index);
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
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
}
