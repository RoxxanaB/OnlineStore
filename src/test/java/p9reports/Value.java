package p9reports;

import java.util.Date;

public class Value {
    public String value1;
    public String value2;
    public String value3;
    public Date value4;

    public Value(String value1, String value2, String value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public Value(String value1, String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Value(Date value4,String value1) {
        this.value1 = value1;
        this.value4 = value4;
    }

    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }
    public void setValue3(String value3) {
        this.value3 = value3;
    }
}
