package p6datacollection;

import p0file.connection.DBConnection;
import p5skeleton.Address;

import java.sql.*;
import java.util.*;

public class AddressCollection {
    private static final String sql_Address = "SELECT * FROM LORO_Address";

    public Address getAddressWithId(int id){
        Address address = new Address();
        for(Address a: getListOfAddressesFromDB()){
            if(a.getAddressId() == id){
                address = a;
            }
        }
        return address;
    }

    public List<Address> getListOfAddressesFromDB() {
        List<Address> addressList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_Address))
        {

            while (rs.next()) {
                addressList.add(new Address(
                        rs.getInt("address_id"), rs.getString("street"), rs.getInt("number"),
                        rs.getString("building"), rs.getString("apartament"),
                        rs.getString("floor"), rs.getString("city"), rs.getString("county"),
                        rs.getString("country"), rs.getInt("zip_code")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }
}