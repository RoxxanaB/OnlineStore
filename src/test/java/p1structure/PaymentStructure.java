package p1structure;

public class PaymentStructure implements Model{
    private String id;
    private String customerId;
    private String cartId;
    private String amount;
    private String paymentType;
    private String paymentDate;
    private String fieldNameError;
    private String message;

    public PaymentStructure(String id, String customerId, String cartId, String amount, String paymentType, String paymentDate) {
        this.id = id;
        this.customerId = customerId;
        this.cartId = cartId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
    }

    public PaymentStructure(String id, String fieldNameError, String message) {
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

    public String getCartId() {
        return cartId;
    }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String getFieldNameError() {
        return fieldNameError;
    }
    public void setFieldNameError(String fieldNameError) {
        this.fieldNameError = fieldNameError;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
