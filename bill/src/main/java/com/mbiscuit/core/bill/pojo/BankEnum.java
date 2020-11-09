package com.mbiscuit.core.bill.pojo;

/**
 * @author wangxiaolei
 */

public enum BankEnum {
    /**
     * 银行枚举
     */
    CMB("招商银行", "5667", 1),
    CCB("建设银行", "9345", 2),
    HXB("华夏银行", "5713", 3),
    BCM("交通银行", "6064", 4),
    BSH("上海银行", "5288", 5),
    CEB("光大银行", "9944", 6),
    SPDB("浦发银行", "8586", 7),
    PAB("平安银行", "4836", 8),
    CGB("广发银行", "6870", 9),
    ;

    private String bankName;

    private String endNumber;

    private Integer sort;

    BankEnum(String bankName, String endNumber, Integer sort) {
        this.bankName = bankName;
        this.endNumber = endNumber;
        this.sort = sort;
    }

    public static void main(String[] args) {
        for (BankEnum bank : BankEnum.values()) {
            System.out.println(bank);
        }
    }

    public String getBankName() {
        return bankName;
    }

    public String getEndNumber() {
        return endNumber;
    }

    public Integer getSort() {
        return sort;
    }
}
