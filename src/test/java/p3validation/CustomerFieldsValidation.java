package p3validation;

import p1structure.CustomerStructure;
import p2convert.CustomerConverter;

import java.util.*;

public class CustomerFieldsValidation extends Validator<CustomerStructure> {

    public void validateModel(String file) {
       List<CustomerStructure> customers = new CustomerConverter().convertLine(file);

        for (CustomerStructure c : customers) {
            setId(c.getId());
            if(checkInteger(c.getId()) && checkString(c.getFirstName(),50) && checkString(c.getLastName(),50) &&
                    checkPhoneNumber(c.getPhone()) && checkCnp(c.getCNP()) && checkInteger(c.getAddressId())){
                    addValidModel(c);
            }else{
                if(!checkInteger(c.getId())){
                    setFieldName("customer_id");
                }else if(!checkString(c.getFirstName(),50)){
                    setFieldName("first_name");
                }else if(!checkString(c.getLastName(),50)) {
                    setFieldName("last_name");
                }else if(!checkPhoneNumber(c.getPhone())){
                    setFieldName("phone");
                }else if(!checkCnp(c.getCNP())){
                    setFieldName("CNP");
                }else if(!checkInteger(c.getPhone())){
                    setFieldName("address_id");
                }
                addInvalidModel(new CustomerStructure(getId(), getFieldName(), getMessage()));
            }
        }
    }

    @Override
    public String toString() {
        return "Customers";
    }
}
