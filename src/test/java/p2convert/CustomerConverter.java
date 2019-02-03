package p2convert;

import com.csvreader.CsvReader;
import p1structure.CustomerStructure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerConverter extends Converter<CustomerStructure>{

    public List<CustomerStructure> convertLine(String fileName){
        List<CustomerStructure> listOfCustomers = new ArrayList<>();
        CsvReader carts = null;
        if(readFile(fileName)){
            try{
                carts = new CsvReader(new FileReader(fileName));
                carts.readHeaders();
                while (carts.readRecord()) {
                    listOfCustomers.add(new CustomerStructure(carts.get("customer_id"), carts.get("first_name"), carts.get("last_name"),
                            carts.get("phone"), carts.get("CNP"),carts.get("address_id"),carts.get("status")));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(carts != null){
                    carts.close();
                }
            }
        }
        return listOfCustomers;
    }
}
