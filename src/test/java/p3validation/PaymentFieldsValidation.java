package p3validation;

import p1structure.PaymentStructure;
import p2convert.PaymentConverter;

import java.util.List;

public class PaymentFieldsValidation extends Validator<PaymentStructure> {

    public void validateModel(String file) {
        List<PaymentStructure> payments = new PaymentConverter().convertLine(file);

        for(PaymentStructure p : payments) {
            setId(p.getId());
            if(checkInteger(p.getId()) && checkInteger(p.getCustomerId()) && checkInteger(p.getCartId()) &&
                    checkDouble(p.getAmount()) && checkPaymentType(p.getPaymentType()) && checkDate(p.getPaymentDate())){
                addValidModel(p);
            }else{
                if(!checkInteger(p.getId())){
                    setFieldName("payment_id");
                }else if(!checkInteger(p.getCustomerId())){
                    setFieldName("customer_id");
                }else if(!checkInteger(p.getCartId())) {
                    setFieldName("cart_id");
                }else if(!checkDouble(p.getAmount())){
                    setFieldName("amount");
                }else if(!checkPaymentType(p.getPaymentType())){
                    setFieldName("payment_type");
                }else if(!checkDate(p.getPaymentDate())){
                    setFieldName("payment_date");
                }
                addInvalidModel(new PaymentStructure(getId(), getFieldName(), getMessage()));
            }
        }
    }

    @Override
    public String toString() {
        return "Payments";
    }
}
