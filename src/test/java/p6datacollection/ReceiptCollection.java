package p6datacollection;

import p0file.connection.DBConnection;
import p5skeleton.PaymentType;
import p5skeleton.ReceiptDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptCollection {
    private static final String sql_Receipts = "SELECT * FROM LORO_Receipts;";

    public static double getAmountWithId(int cartId){
        double amount = 0;
        for(ReceiptDB r: getReceiptsFromDB()){
            if(r.getCartId() == cartId){
                amount += r.getAmount();
            }
        }
        return amount;
    }

    public Blob getReceiptWithCartId(int cartId){
        Blob receipt = null;
        for(ReceiptDB r: getReceiptsFromDB()){
            if(r.getCartId() == cartId){
                receipt = r.getReceipt();
            }
        }
        return receipt;
    }

    public static List<ReceiptDB> getReceiptsFromDB(){
        List<ReceiptDB> receiptList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try(Connection conn = DBConnection.getConnection()){
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql_Receipts);

            while(rs.next()){
                receiptList.add(new ReceiptDB(rs.getInt("receipt_id"), rs.getInt("cart_id"),
                           rs.getBlob("receipt"), rs.getDouble("amount"),
                        PaymentType.valueOf(rs.getString("payment_type"))));

            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return receiptList;
    }
}
