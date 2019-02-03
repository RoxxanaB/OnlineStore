package p4import;

import p0file.connection.DBConnection;
import p1structure.CartStructure;
import p3validation.CartFieldsValidation;
import p3validation.InvalidData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImportCarts{
    public static final String sql_Carts_Insertion =
        "INSERT INTO LORO_Carts(cart_id, customer_id, product_id, quantity, date_added, price) " +
                "VALUES (?,?,?,?,?,?);";

    public void loadCartsFromCSV(String file){
        CartFieldsValidation carts = new CartFieldsValidation();

        InvalidData invalidData = new InvalidData();
        invalidData.writeErrorsToFile(file, carts);

        List<CartStructure> validCarts = carts.getValidList();

        for(CartStructure c: validCarts){
            try(Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql_Carts_Insertion))
            {
                int idx = 0;
                pstmt.setInt(++idx, Integer.parseInt(c.getId()));
                pstmt.setInt(++idx, Integer.parseInt(c.getCustomerId()));
                pstmt.setInt(++idx, Integer.parseInt(c.getProductId()));
                pstmt.setInt(++idx, Integer.parseInt(c.getQuantity()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                pstmt.setObject(++idx, LocalDate.parse(c.getDateAdded(), formatter));
                pstmt.setDouble(++idx, Double.valueOf(c.getPrice()));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
public boolean checkTheExistenceOfCartInDB(int cartId){
        boolean result = false;
        List<CartItem> carts = CartItemCollection.getOrderedProductsFromDB();
        if(carts == null){
            return result;
        }
        for(CartItem c: carts){
            if(c.getCartId() == cartId){
                result = true;
            }
        }
        return result;
    }
 */
