package p7receipt.format;

import p5skeleton.*;
import p6datacollection.CartItemCollection;
import p6datacollection.CustomerCollection;
import p6datacollection.PaymentCollection;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Cart {
    private List<CartItem> cartItemList = new CartItemCollection().getOrderedProductsFromDB();
    private List<Payment> paymentList = new PaymentCollection().getPaymentsFromDB();
    private static final DecimalFormat df = new DecimalFormat("#.##");
    private String status = "";

    public static Provider getProvider(){
        return new CartItem().getProvider();
    }

    public LocalDate getDateFromDB(int cartId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = null;
        for(CartItem c: getCartItemList()){
            if(c.getCartId() == cartId){
                date = LocalDate.parse(c.getDateAdded() +"", formatter);
            }
        }
        return date;
    }

    public PaymentType getPaymentType(int cartId){
        PaymentType type = PaymentType.CASH;
        for(Payment p: paymentList){
            if(p.getCartId() == cartId){
                type = p.getType();
            }
        }
        return type;
    }

    public double getTotalPayment(int cartId){
        double totalReceipt = getTotalPaymentFromCart(cartId);
        double totalPayment = Double.valueOf(df.format(getTotalPaymentFromPaymentList(cartId)));
        double total = totalReceipt - totalPayment;
            if(total == totalReceipt){
                status = "Neachitat.\nPlata se face la livrare.";
            }else if(total <= 0){
                status = "Achitat integral.\nPlata s-a facut online";
            }else if(total > 0 && total < totalReceipt){
                status = "Plata incompleta.\nS-au achitat: " + String.format("%.2f",totalPayment) + " RON din "
                        + String.format("%.2f",totalReceipt) + " RON.";
            }
        return Double.valueOf(df.format(total));
    }

    public double getTotalPaymentFromPaymentList(int cartId) {
        double totalPayment = 0;
        for(Payment p: paymentList){
            if(p.getCartId() == cartId){
                totalPayment = p.getAmount();
                break;
            }
        }
        return totalPayment;
    }

    public double getTotalPaymentFromCart(int cartId) {
        double totalReceipt = 0;
        for (Map.Entry<Integer, List<CartItem>> entry : getCartItemsById().entrySet()) {
            if (entry.getKey() == cartId) {
                for (CartItem c : entry.getValue()) {
                    totalReceipt += c.calculateTotalPayment();
                }
                break;
            }
        }
        return Double.valueOf(df.format(totalReceipt));
    }

    public double getVatValueForId(int cartId){
        double vatValue = 0;
        for (Map.Entry<Integer, List<CartItem>> entry : getCartItemsById().entrySet()) {
            if (entry.getKey() == cartId) {
                for (CartItem c : entry.getValue()) {
                    vatValue += c.calculateValueForVAT();
                }
                break;
            }
        }
        return vatValue;
    }

    public double getValueForId(int cartId){
        double value = 0;
        for (Map.Entry<Integer, List<CartItem>> entry : getCartItemsById().entrySet()) {
            if (entry.getKey() == cartId) {
                for (CartItem c : entry.getValue()) {
                    value += c.calculateValueForProducts();
                }
                break;
            }
        }
        return value;
    }

    public Map<Integer,List<CartItem>> getCartItemsById(){
        Map<Integer, List<CartItem>>  cartMap = new HashMap<>();
        for(CartItem c: cartItemList){
            List<CartItem> carts = cartMap.get(c.getCartId());
            if(carts == null){
                carts = new ArrayList<>();
                cartMap.put(c.getCartId(),carts);
            }
            carts.add(c);
        }
        return cartMap;
    }

    public Map<Integer, Double> getSubtotalForEachCategory(int cartId){
        Map<Integer, Double> categoryMap = new HashMap<>();
        for(CartItem c: getCartItemWithId(cartId)){
            Double subtotal = categoryMap.get(c.getCategoryId());
            if(subtotal == null){
                subtotal = 0D;
            }
            subtotal += c.calculateTotalPayment();
            categoryMap.put(c.getCategoryId(), Double.valueOf(df.format(subtotal)));
        }
        return categoryMap;
    }

    public Map<Integer, List<Integer>> getCategoriesForCartId(){
        Map<Integer, List<Integer>> categoryMap = new HashMap<>();
        for(CartItem c: cartItemList){
            List<Integer> categoryIds = categoryMap.get(c.getCartId());
            if(categoryIds == null){
                categoryIds = new ArrayList<>();
                categoryMap.put(c.getCartId(),categoryIds);
            }
            categoryIds.add(c.getCategoryId());
        }
        return categoryMap;
    }

    public Customer getCustomerWithCartId(int cartId){
        int customerId = 0;
        for(CartItem c: cartItemList){
            if(c.getCartId() == cartId){
                customerId = c.getCustomerId();
            }
        }
        return new CustomerCollection().getCustomerWithId(customerId);
    }

    public List<CartItem> getCartItemWithId(int cartId){
        List<CartItem> items = new ArrayList<>();
        for(CartItem c: cartItemList){
            if(c.getCartId() == cartId){
                items.add(c);
            }
        }
        return items;
    }

    public List<CartItem> getCartItemList() { return cartItemList; }
    public void setCartItemList(List<CartItem> cartItemList) { this.cartItemList = cartItemList; }

    public List<Payment> getPaymentList() { return paymentList; }
    public void setPaymentList(List<Payment> paymentList) { this.paymentList = paymentList; }

    public static DecimalFormat getDf() {
        return df;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}


/*
    public double getVatRateForId(int cartId){
        double vatRate = 0;
        for(CartItem c: getCartItemWithId(cartId)){
            if(c.getCartId() == cartId){
                vatRate += c.calculateVatRateForOneProduct();
            }
        }
        return vatRate;
    }

public double getVatValueForId(int cartId){
        double vatValue = 0;
        for(CartItem c: getCartItemWithId(cartId)){
            if(c.getCartId() == cartId){
                vatValue += c.calculateValueForVAT();
            }
        }
        return vatValue;
    }


    public double getTotalPaymentForId(int cartId){
        double total = 0;
        for(CartItem c: getCartItemWithId(cartId)){
            if(c.getCartId() == cartId){
                total += c.calculateTotalPayment();
            }
        }
        return total;
    }
 */