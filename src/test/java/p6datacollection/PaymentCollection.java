package p6datacollection;

import p0file.connection.DBConnection;
import p5skeleton.Payment;
import p5skeleton.PaymentType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PaymentCollection {
    private static final String sql_Payments = "SELECT * FROM LORO_Payments;";

    public static List<Payment> getPaymentsFromDB(){
        List<Payment> paymentList = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_Payments))
        {
            while(rs.next()){
                paymentList.add(new Payment(
                        rs.getInt("payment_id"), rs.getInt("customer_id"),
                        rs.getInt("cart_id"),rs.getDouble("amount"),
                        PaymentType.valueOf(rs.getString("payment_type")),
                        rs.getDate("payment_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentList;
    }
}
