package p5skeleton;

import java.sql.Date;

public class Payment {
    private int paymentId;
    private int customerId;
    private int cartId;
    private double amount;
    private PaymentType type;
    private Date date;

    private static final String currencyCode = "RON";

    public Payment(int paymentId, int customerId, int cartId, double amount, PaymentType type, Date date) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.cartId = cartId;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentType getType() {
        return type;
    }
    public void setType(PaymentType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public static String getCurrencyCode() {
        return currencyCode;
    }
}
