package p1structure;

public class CustomerStructure implements Model{
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String CNP;
    private String addressId;
    private String status;
    private String fieldNameError;
    private String message;

    public CustomerStructure(String id, String firstName, String lastName, String phone, String CNP, String addressId, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.CNP = CNP;
        this.addressId = addressId;
        this.status = status;
    }

    public CustomerStructure(String id, String fieldNameError, String message) {
        this.id = id;
        this.fieldNameError = fieldNameError;
        this.message = message;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddressId() {
        return addressId;
    }
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getFieldNameError() { return fieldNameError; }
    public void setFieldNameError(String fieldNameError) { this.fieldNameError = fieldNameError; }

    @Override
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
