package p5skeleton;

public class ReceiptDetail {
    private int receiptId;
    private int cartId;
    private int categoryId;
    private String categoryName;
    private double subtotal;

    public ReceiptDetail(int receiptId, int cartId, int categoryId, String categoryName, double subtotal) {
        this.receiptId = receiptId;
        this.cartId = cartId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subtotal = subtotal;
    }

    public int getReceiptId() { return receiptId; }
    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
