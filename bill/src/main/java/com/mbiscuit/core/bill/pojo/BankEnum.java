package com.mbiscuit.core.bill.pojo;

/**
 * @author wangxiaolei
 */

public enum BankEnum {
    /**
     * 银行枚举
     */
    CMB("招商银行", "5667"),
    CCB("建设银行", "9345"),
    HXB("华夏银行", "5713"),
    BCM("交通银行", "6064"),
    BSH("上海银行", "5288"),
    CEB("光大银行", "9944"),
    SPDB("浦发银行", "8586"),
    PAB("平安银行", "4836"),
    CGB("广发银行", "6870"),
    ;

    private String bankName;

    private String endNumber;

    BankEnum(String bankName, String endNumber) {
        this.bankName = bankName;
        this.endNumber = endNumber;
    }

    public static void main(String[] args) {
        for (BankEnum bank : BankEnum.values()) {
            System.out.println(bank);
        }
    }

}
