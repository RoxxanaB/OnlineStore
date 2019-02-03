package p4import;

import p0file.connection.DBConnection;
import p1structure.PaymentStructure;
import p3validation.InvalidData;
import p3validation.PaymentFieldsValidation;
import p5skeleton.Payment;
import p6datacollection.PaymentCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImportPayments {
    public static final String sql_Payments_Insertion =
            "INSERT INTO LORO_Payments(payment_id, customer_id, cart_id, amount, payment_type, payment_date) " +
                    "VALUES (?,?,?,?,?,?);";

    public void loadPaymentsFromCSV(String file){
        PaymentFieldsValidation payments = new PaymentFieldsValidation();

        InvalidData invalidData = new InvalidData();
        invalidData.writeErrorsToFile(file, payments);

        List<PaymentStructure> validPayments = payments.getValidList();

        for(PaymentStructure m: validPayments)
        {
            if(!checkTheExistenceOfPaymentInDB(Integer.parseInt(m.getId()))) {
                try(Connection conn = DBConnection.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql_Payments_Insertion))
                {
                    int idx = 0;
                    pstmt.setInt(++idx, Integer.parseInt(m.getId()));
                    pstmt.setInt(++idx, Integer.parseInt(m.getCustomerId()));
                    pstmt.setInt(++idx, Integer.parseInt(m.getCartId()));
                    pstmt.setDouble(++idx, Double.valueOf(m.getAmount()));
                    pstmt.setString(++idx, m.getPaymentType());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    pstmt.setObject(++idx, LocalDate.parse(m.getPaymentDate(), formatter));
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                invalidData.writeDuplicateToLogFile(m.getId());
            }
        }
    }

    public boolean checkTheExistenceOfPaymentInDB(int paymentId){
        boolean result = false;
        List<Payment> payments = PaymentCollection.getPaymentsFromDB();
        if(payments == null){
            return result;
        }
        for(Payment p: payments){
            if(p.getPaymentId() == paymentId){
                result = true;
            }
        }
        return result;
    }
}
