package p5skeleton;

public class Address {
    private int addressId;
    private String street;
    private int number;
    private String building;
    private String apartament;
    private String floor;
    private String city;
    private String county;
    private String country;
    private int zipCode;

    public Address(int addressId, String street, int number, String building, String apartament,
                   String floor, String city, String county, String country, int zipCode) {
        this.addressId = addressId;
        this.street = street;
        this.number = number;
        this.building = building;
        this.apartament = apartament;
        this.floor = floor;
        this.city = city;
        this.county = county;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address(){}

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartament() { return apartament; }
    public void setApartament(String apartament) { this.apartament = apartament; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }

    public int getZipCode() {
        return zipCode;
    }
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void addAddress(){}
    public void removeAddress(){}
    public void updateAddress(){}

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", building='" + building + '\'' +
                ", apartament='" + apartament + '\'' +
                ", floor='" + floor + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", country='" + country + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
