package p2convert;

import com.csvreader.CsvReader;
import p1structure.CartStructure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartConverter extends Converter<CartStructure> {

    public List<CartStructure> convertLine(String fileName){
        List<CartStructure> listOfCarts = new ArrayList<>();
        CsvReader carts = null;
        if(readFile(fileName)){
            try{
                carts = new CsvReader(new FileReader(fileName));
                carts.readHeaders();
                while (carts.readRecord()) {
                    listOfCarts.add(new CartStructure(carts.get("cart_id"), carts.get("customer_id"), carts.get("product_id"),
                            carts.get("quantity"), carts.get("date_added"),carts.get("price")));
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
        return listOfCarts;
    }
}
