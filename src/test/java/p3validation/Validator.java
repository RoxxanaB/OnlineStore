package p3validation;

import p1structure.Model;
import p5skeleton.PaymentType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.List;

public abstract class Validator<T> {
    private static String id;
    private static String message = "";
    private static String fieldName;

    private List<T> validList = new ArrayList<>();
    private List<Model> invalidList = new ArrayList<>();

    public void addValidModel(T model){
        validList.add(model);
    }
    public List<T> getValidList(){
        return new ArrayList<>(validList);
    }

    public void addInvalidModel(Model model){
        invalidList.add(model);
    }
    public List<Model> getInvalidList(){
        return new ArrayList<>(invalidList);
    }

    public abstract void validateModel(String file);


    public static boolean checkPaymentType(String payment)
    {
        if(payment == ""){
            message = "Found 'Null'";
            return false;
        }
        payment = payment.toUpperCase().replaceAll("\\s+","");
        if(!payment.equals(PaymentType.CASH.toString().toUpperCase()) &&
                !payment.equals(PaymentType.CREDIT_CARD.toString().toUpperCase()) &&
                  !payment.equals(PaymentType.PAY_PAL.toString().toUpperCase())){
            message = "Wrong payment type. Found: '" + payment + "'";
            return false;
        }
        return true;
    }


    public static boolean checkCnp(String cnp)
    {
        if (cnp == "") { return true; }
        if (!checkLong(cnp)) { return false; }

        if (cnp.length() != 13) {
            message = "CNP should have 13 digits. Found " +cnp.length() + " digits";
            return false;
        }
        if(!checkControlNumber(cnp)){
            return false;
        }
        return true;
    }


    public static boolean checkLong(String field)
    {
        for (char c : field.toCharArray()) {
            if (c < '0' || c > '9') {
                message = "Not a number. Found: " + c;
                return false;
            }
        }
        try {
            Long id = Long.valueOf(field);
        } catch (NumberFormatException e) {
            message = "Wrong format";
            return false;
        }
        return true;
    }

    private static boolean checkControlNumber(String cnp)
    {
        final String n = "279146358279";
        int suma = 0;
        for (int i = 0; i < 12; i++) {
            suma += Integer.parseInt(cnp.substring(i, i+1)) * Integer.parseInt(n.substring(i, i+1));
        }
        if( suma % 11 != 10  &&  suma % 11 != Integer.parseInt(cnp.substring(12, 13)) ){
            message = "Wrong control number";
            return false;
        }
        return true;
    }

    public static boolean checkPhoneNumber(String field)
    {
        if(field == ""){
            message = "Found 'Null'";
            return false;
        }
        if(!checkLong(field)){ return false; }

        if(!field.startsWith("0")) { field = "0" + field; }

        if(field.length() != 10){
            message = "Found " + (field.length() - 1) + " digits. Invalid phone number";
            return false;
        }
        if(!field.startsWith("07")){
            message = "Phone number should start with '07..'. Found '" + field.substring(1,field.length()) +"'";
            return false;
        }
        return true;
    }


    public static boolean checkString(String field, int length)
    {
        if(field == ""){
            message = "Found 'Null'";
            return false;
        }
        if(field.length() > length){
            message = "Maximum addmited length: " + length + ". Found " + field.length() + " characters" ;
            return false;
        }
        if(!Pattern.matches("^[ A-Za-z]+$",field.replaceAll("[-]"," "))) {
            message = "Found '" + field + "'. Only letters admitted";
            return false;
        }
        if(!hasAtLeastOneVowelAtEveryFourLetters(field)){
            message = "Required at least one vowel at every four letters. Found '" + field + "'";
            return false;
        }
        return true;
    }


    private static boolean hasAtLeastOneVowelAtEveryFourLetters(String string)
    {
        return Stream.iterate(0, i -> i + 1)
                .limit(string.length() - 3)
                .allMatch(it -> string.toLowerCase()
                        .substring(it, it + 4)
                        .matches(".*[aeiou].*"));
    }

    public static boolean checkDouble(String field)
    {
        if(field == ""){
            message = "Found 'Null'";
            return false;
        }else {
            try {
                double id = Double.valueOf(field);
            } catch (NumberFormatException e) {
                message = "Found '" + field + "'. Wrong format";
                return false;
            }
        }
        return true;
    }

    public static boolean checkInteger(String field)
    {
        if (field == "") {
            message = "Found 'Null'";
            return false;
        }
        if(!checkIntegerNumber(field)){ return false; }
        return true;
    }

    public static boolean checkIntegerNumber(String field)
    {
        for (char c : field.toCharArray()) {
            if (c < '0' || c > '9') {
                message = "Not a number. Found: " + c;
                return false;
            }
        }
        try {
            int id = Integer.parseInt(field);
        } catch (NumberFormatException e) {
            message = "Found " + field + ". Wrong format";
            return false;
        }
        return true;
    }

    public static boolean checkNonZeroInteger(String field)
    {
        if(!checkInteger(field)){ return false; }
        if(Integer.parseInt(field) == 0){
            message = "Found '0'";
            return false;
        }
        return true;
    }


    public static boolean checkDate(String field)
    {
        if(field == ""){
            message = "Found 'Null'";
            return false;
        }
        if(field.length() != 10){
            message = "Wrong format";
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(field, formatter);
        }catch (DateTimeParseException e){
            message = e.getLocalizedMessage();
            return false;
        }
        return true;
    }

    public static String getId() {
        return id;
    }
    public static void setId(String id) {
        Validator.id = id;
    }

    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        Validator.message = message;
    }

    public static String getFieldName() {
        return fieldName;
    }
    public static void setFieldName(String fieldName) {
        Validator.fieldName = fieldName;
    }
}
