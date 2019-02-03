package p2convert;

import com.csvreader.CsvReader;
import p1structure.PaymentStructure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentConverter extends Converter<PaymentStructure>{

    public List<PaymentStructure> convertLine(String fileName){
        List<PaymentStructure> listOfPayments = new ArrayList<>();
        CsvReader carts = null;
        if(readFile(fileName)){
            try{
                carts = new CsvReader(new FileReader(fileName));
                carts.readHeaders();
                while (carts.readRecord()) {
                    listOfPayments.add(new PaymentStructure(carts.get("payment_id"), carts.get("customer_id"),
                            carts.get("cart_id"), carts.get("amount"),
                            carts.get("payment_type"), carts.get("payment_date")));
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
        return listOfPayments;
    }
}
