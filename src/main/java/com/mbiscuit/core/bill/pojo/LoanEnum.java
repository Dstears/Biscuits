package com.mbiscuit.core.bill.pojo;

public enum  LoanEnum {

    CAR(1,"车贷");

    private Integer type;

    private String name;

    LoanEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }
}
