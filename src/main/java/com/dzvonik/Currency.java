package com.dzvonik;

public class Currency {

    private int id;
    private String code;
    private String full_name;
    private String sign;

    public Currency(int id, String code, String full_name, String sign) {
        this.id = id;
        this.code = code;
        this.full_name = full_name;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getSign() {
        return sign;
    }

}
