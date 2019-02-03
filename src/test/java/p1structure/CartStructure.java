package p1structure;

public class CartStructure implements Model{
    private String id;
    private String customerId;
    private String productId;
    private String quantity;
    private String dateAdded;
    private String price;
    private String fieldNameError;
    private String message;

    public CartStructure(String id, String customerId, String productId, String quantity, String dateAdded, String price) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.price = price;
    }

    public CartStructure(String id, String fieldNameError, String message) {
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

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String getFieldNameError() { return fieldNameError; }
    public void setFieldNameError(String fieldNameError) { this.fieldNameError = fieldNameError; }

    @Override
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
