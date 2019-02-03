package p5skeleton;

import p6datacollection.AddressCollection;

public class Customer{
    private int customerID;
    private String firstName;
    private String lastName;
    private String phone;
    private String CNP;
    private int addressId;

    public Customer(int customerID, String firstName, String lastName, String phone, String CNP, int addressId) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.CNP = CNP;
        this.addressId = addressId;
    }

    public Customer(){}

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCNP() {
        return CNP;
    }
    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        AddressCollection addressList = new AddressCollection();
        Address deliveryAddress = addressList.getAddressWithId(addressId);

        StringBuilder sb = new StringBuilder();
        sb.append("Customer:       ").append(firstName + " " + lastName).append("\n");
        sb.append("Contact:        ").append(phone).append("\n");
        sb.append("CNP:            ").append(CNP).append("\n");
        sb.append("Adresa:         ").append(deliveryAddress.getStreet() + ", " + deliveryAddress.getNumber() + ", ").append("\n");;
        sb.append("                ").append(deliveryAddress.getBuilding() + "," + deliveryAddress.getApartament() + ", " + deliveryAddress.getFloor()).append("\n");
        sb.append("Judet:          ").append(deliveryAddress.getCounty()).append("\n");
        sb.append("Localitate:     ").append(deliveryAddress.getCity()).append("\n");

        return sb.toString();
    }
}

/*
 public Address getAddressWithCartId(int cartId){
        int addressId = 0;
        for(CartItem c: getOrderedProductsFromDB()){
            if(c.getCartId() == cartId){
                addressId = c.get
            }
        }
        return items;
        Address address = new Address();
        for(Address a: addressList){
            if(a.getAddressId() == id){
                address = a;
            }
        }
        return address;
    }
 */
