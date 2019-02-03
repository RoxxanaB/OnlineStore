package p5skeleton;

import p6datacollection.ProviderCollection;

import java.sql.*;

public class CartItem{
    private int cartId;
    private int customerId;
    private int productId;
    private int quantity;
    private String productDescription;
    private double price;
    private Date dateAdded;
    private int categoryId;
    private int subcategoryId;

    private static final Provider provider = new ProviderCollection().getProviderWithId(1);
    private static final String UM = "buc";

    public CartItem(int cartId, int customerId, int productId, int quantity, String productDescription,
                    double price, Date dateAdded, int categoryId, int subcategoryId) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.productDescription = productDescription;
        this.price = price;
        this.dateAdded = dateAdded;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }

    public CartItem(){}

    public double calculateVatRateForOneProduct(){
        return provider.getVATRate() / 100.0 * price;
    }

    public double calculateValueForProducts(){
        return quantity * price;
    }

    public double calculateValueForVAT(){
        return quantity * calculateVatRateForOneProduct();
    }

    public double calculateTotalPayment(){
        return calculateValueForProducts() + calculateValueForVAT();
    }

    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }
    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public static String getUM() {
        return UM;
    }

    public Provider getProvider() {
        return provider;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartId=" + cartId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", dateAdded=" + dateAdded +
                ", categoryId=" + categoryId +
                ", subcategoryId=" + subcategoryId +
                '}';
    }
}
