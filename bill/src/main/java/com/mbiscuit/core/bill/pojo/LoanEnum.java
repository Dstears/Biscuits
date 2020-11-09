package com.mbiscuit.core.bill.pojo;

import java.util.Objects;

public enum LoanEnum {

    CAR(1, "车贷"),
    JIEBEI(2, "借呗");

    private Integer type;

    private String name;

    LoanEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static LoanEnum valueOf(Integer type) {
        for (LoanEnum value : LoanEnum.values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}
