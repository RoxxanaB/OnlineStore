package p6datacollection;

import p0file.connection.DBConnection;
import p5skeleton.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerCollection {
    private static final String sql_Customers =
            "SELECT * FROM LORO_Customers A " +
            "JOIN LORO_Carts B ON A.customer_id = B.customer_id";

    public Customer getCustomerWithId(int customerId){
        Customer customer = new Customer();
        for(Customer c: getCustomersFromDB()){
            if(c.getCustomerID() == customerId){
                customer = c;
            }
        }
        return customer;
    }

    public static List<Customer> getCustomersFromDB(){
        List<Customer> customerList = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_Customers))
        {
            while(rs.next()){
                customerList.add(new Customer(
                        rs.getInt("customer_id"), rs.getString("first_name"),
                        rs.getString("last_name"),rs.getString("phone"),
                        rs.getString("CNP"),rs.getInt("address_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}

/*

    public void getCustomerFromDB(int id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try(Connection conn = DBConnection.getConnection()){
            String SQL = "SELECT B.first_name, B.last_name, B.phone, B.CNP, B.address_id, B.status " +
                    "FROM LORO_Shopping_Cart A " +
                    "JOIN LORO_Customers B ON A.customer_id = B.customer_id " +
                    "WHERE order_id = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while(rs.next()){
                setFirstName(rs.getString("first_name"));
                setLastName(rs.getString("last_name"));
                setPhone(rs.getString("phone"));
                setCNP(rs.getString("CNP"));
                setAddressId(rs.getInt("address_id"));
                setStatus(rs.getString("status"));
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
 */
