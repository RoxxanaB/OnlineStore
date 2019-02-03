package p5skeleton;

import java.sql.Blob;

public class ReceiptDB {
    private int receiptId;
    private int cartId;
    private Blob receipt;
    private double amount;
    private PaymentType payment_type;

    public ReceiptDB(int receiptId, int cartId, Blob receipt, double amount, PaymentType payment_type) {
        this.receiptId = receiptId;
        this.cartId = cartId;
        this.receipt = receipt;
        this.amount = amount;
        this.payment_type = payment_type;
    }

    public int getReceiptId() {
        return receiptId;
    }
    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Blob getReceipt() {
        return receipt;
    }
    public void setReceipt(Blob receipt) {
        this.receipt = receipt;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentType getPayment_type() {
        return payment_type;
    }
    public void setPayment_type(PaymentType payment_type) {
        this.payment_type = payment_type;
    }
}
