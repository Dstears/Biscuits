package com.mbiscuit.core.bill.pojo;

public enum BillDetailTypeEnum {
    NORMAL(0, "正常计算"),
    MERGE(1, "合并计算");

    private Integer type;

    private String content;

    BillDetailTypeEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
