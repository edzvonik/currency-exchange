package com.dzvonik.model;

public class Currency {

    private int id;
    private String code;
    private String fullName;
    private String sign;

    public Currency(int id, String code, String full_name, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = full_name;
        this.sign = sign;
    }

    public Currency(String code, String full_name, String sign) {
        this.code = code;
        this.fullName = full_name;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

}
