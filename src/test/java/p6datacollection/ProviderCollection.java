package p6datacollection;

import p0file.connection.DBConnection;
import p5skeleton.Provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProviderCollection{
    private static final String sql_Provider_List = "SELECT * FROM LORO_Provider";

    public Provider getProviderWithId(int id){
        Provider provider = new Provider();
        for(Provider p: getListOfProvideresFromDB()){
            if(p.getProviderId() == id){
                provider = p;
            }
        }
        return provider;
    }

    public List<Provider> getListOfProvideresFromDB() {
        List<Provider> providerList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_Provider_List))
        {
            while (rs.next()) {
                providerList.add(new Provider(
                        rs.getInt("provider_id"), rs.getString("name"),
                        rs.getString("fiscal_code"), rs.getString("trade_register"),
                        rs.getInt("address_id"), rs.getInt("capital"),
                        rs.getString("phone"), rs.getString("bank"),
                        rs.getString("account"), rs.getInt("vat_rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return providerList;
    }
}
