package hr.java.production.enums;

public enum City {
    ZAGREB("Zagreb", "10000"),
    BEDEKOVCINA("Bedekovčina", "49221"),
    ZABOK("Zabok", "49225"),
    SPLIT("Split", "48661"),
    PULA("Pula", "98772"),
    VARAZDIN("Varaždin", "38992");


    private final String cityName;
    private final String postalCode;

    City(String cityName, String postalCode) {
        this.cityName = cityName;
        this.postalCode = postalCode;
    }

    public String cityName() {
        return cityName;
    }

    public String postalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return postalCode.concat(" ").concat(cityName);
    }
}
