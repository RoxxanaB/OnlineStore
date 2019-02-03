package p6datacollection;

import p0file.connection.DBConnection;
import p5skeleton.CartItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CartItemCollection{
    private static final String sql_Products =
            "SELECT A.cart_id, A.customer_id, A.product_id, A.quantity, A.price, A.date_added, " +
            "B.name, B.description, B.category_id, B.subcategory_id " +
            "FROM LORO_Carts A " +
            "JOIN LORO_Products B ON A.product_id = B.product_id;";


    public static List<CartItem> getOrderedProductsFromDB() {
        List<CartItem> orderedProducts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_Products))
        {
            while (rs.next()) {
                String productDescription = rs.getString("name") + " " + rs.getString("description");
                orderedProducts.add(
                        new CartItem(
                                rs.getInt("cart_id"),
                                rs.getInt("customer_id"),
                                rs.getInt("product_id"),
                                rs.getInt("quantity"),
                                productDescription,
                                rs.getDouble("price"),
                                rs.getDate("date_added"),
                                rs.getInt("category_id"),
                                rs.getInt("subcategory_id")
                        ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderedProducts;
    }


}


/*
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public Product getProductFromDB(int id){
        Product product = new Product();
        try(Connection conn = DBConnection.getConnection()){
            String SQL = "SELECT product_id, name, brand, price, color, size, description, category_id, subcategory_id\n " +
                    "FROM LORO_Products " +
                    "WHERE product_id = ? ";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while(rs.next()){
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getDouble("price"));
                product.setColor(rs.getString("color"));
                product.setSize(rs.getString("size"));
                product.setDescription(rs.getString("description"));
                product.setSubcategoryId (rs.getInt("category_id"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }



     public Map<CartItem, Double> getVatRateForEachItem(){
        Map<CartItem, Double> mapOfVatRate = new HashMap<>();
        for(CartItem c: orderedProducts){
            Double vatRate = mapOfVatRate.get(c.calculateVatRateForOneProduct());
            if(vatRate == null){
                vatRate = 0D;
            }
            mapOfVatRate.put(c,vatRate);
        }
        return mapOfVatRate;
    }

    public Map<CartItem, Double> getValueForEachItem(){
        Map<CartItem, Double> mapOfVatRate = new HashMap<>();
        for(CartItem c: orderedProducts){
            Double vatRate = mapOfVatRate.get(c.calculateValueForProducts());
            if(vatRate == null){
                vatRate = 0D;
            }
            mapOfVatRate.put(c,vatRate);
        }
        return mapOfVatRate;
    }

    public Map<CartItem, Double> getVatValueForEachItem(){
        Map<CartItem, Double> mapOfVatRate = new HashMap<>();
        for(CartItem c: orderedProducts){
            Double vatRate = mapOfVatRate.get(c.calculateValueForVAT());
            if(vatRate == null){
                vatRate = 0D;
            }
            mapOfVatRate.put(c,vatRate);
        }
        return mapOfVatRate;
    }

 */