package p3validation;

import p1structure.CartStructure;
import p2convert.CartConverter;

import java.util.*;

public class CartFieldsValidation extends Validator<CartStructure> {

    public void validateModel(String file) {
        List<CartStructure> carts = new CartConverter().convertLine(file);

        for (CartStructure c: carts) {
            setId(c.getId());
            if(checkInteger(c.getId()) && checkInteger(c.getCustomerId()) && checkInteger(c.getProductId()) &&
                    checkNonZeroInteger(c.getQuantity()) && checkDate(c.getDateAdded()) && checkDouble(c.getPrice())){
                addValidModel(c);
            }else{
                if(!checkInteger(c.getId())){
                    setFieldName("cart_id");
                }else if(!checkInteger(c.getCustomerId())){
                    setFieldName("customer_id");
                }else if(!checkInteger(c.getProductId())) {
                    setFieldName("product_id");
                }else if(!checkNonZeroInteger(c.getQuantity())){
                    setFieldName("quantity");
                }else if(!checkDate(c.getDateAdded())){
                    setFieldName("date_added");
                }else if(!checkDouble(c.getPrice())){
                    setFieldName("price");
                }
                addInvalidModel(new CartStructure(getId(), getFieldName(), getMessage()));
            }
        }
    }

    @Override
    public String toString() {
        return "Carts";
    }
}
