package p4import;

import p0file.connection.DBConnection;
import p1structure.CustomerStructure;
import p3validation.CustomerFieldsValidation;
import p3validation.InvalidData;
import p5skeleton.Customer;
import p6datacollection.CustomerCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ImportCustomers {
    public static final String sql_Customer_Insertion =
            "INSERT INTO LORO_Customers(customer_id, first_name, last_name, phone, CNP, address_id, status) " +
                    "VALUES (?,?,?,?,?,?,?);";

    public void loadCustomersFromCSV(String file){
        CustomerFieldsValidation customers = new CustomerFieldsValidation();

        InvalidData invalidData = new InvalidData();
        invalidData.writeErrorsToFile(file, customers);

        List<CustomerStructure> validCustomers = customers.getValidList();
        PreparedStatement pstmt = null;
        try(Connection conn = DBConnection.getConnection()){
            for(CustomerStructure m: validCustomers) {
                if(!checkTheExistenceOfCustomerInDB(Integer.parseInt(m.getId())))
                {
                    pstmt = conn.prepareStatement(sql_Customer_Insertion);
                    int idx = 0;
                    pstmt.setInt(++idx, Integer.parseInt(m.getId()));
                    pstmt.setString(++idx, m.getFirstName());
                    pstmt.setString(++idx, m.getLastName());
                    pstmt.setString(++idx, m.getPhone());
                    pstmt.setString(++idx, m.getCNP());
                    pstmt.setInt(++idx, Integer.parseInt(m.getAddressId()));
                    pstmt.setString(++idx, m.getStatus());
                    pstmt.executeUpdate();
                } else {
                    invalidData.writeDuplicateToLogFile(m.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkTheExistenceOfCustomerInDB(int customerId){
        boolean result = false;
        List<Customer> customers = CustomerCollection.getCustomersFromDB();
        if(customers == null){
            return result;
        }
        for(Customer c: customers){
            if(c.getCustomerID() == customerId){
                result = true;
            }
        }
        return result;
    }
}

 /*InvalidCustomers invalidCustomers = new InvalidCustomers();
        invalidCustomers.writeErrorsToFile(file);
        List<CsvStructure> validModels = invalidCustomers.getValidation().getValidModels(file);*/