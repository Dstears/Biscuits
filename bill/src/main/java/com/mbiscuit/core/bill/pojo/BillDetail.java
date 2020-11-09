package com.mbiscuit.core.bill.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amountPerStage;

    private Integer stage;

    private BigDecimal interestPerStage;

    private BigDecimal amount;

    private BigDecimal interest;

    private BigDecimal total;

    private String bankCode;

    /**
     * 0-正常统计，1-计算时累计统计
     */
    private Integer type;

    private Long billSummaryId;

    public void changeAmountPerStage(BigDecimal amountPerStage) {
        this.amountPerStage = amountPerStage;
        BigDecimal stageDecimal = BigDecimal.valueOf(stage);
        this.amount = this.amountPerStage.multiply(stageDecimal);
        this.total = this.amount.add(this.interest);
    }

    public static BillDetail getInstance(BigDecimal amountPerStage, Integer stage, BigDecimal interestPerStage, BillDetailTypeEnum type, BankEnum bank) {
        BillDetail one = new BillDetail();
        one.amountPerStage = amountPerStage;
        one.stage = stage;
        one.type = type.getType();
        BigDecimal stageDecimal = BigDecimal.valueOf(stage);
        one.amount = amountPerStage.multiply(stageDecimal);
        one.interestPerStage = interestPerStage;
        one.interest = interestPerStage.multiply(stageDecimal);
        one.total = one.amount.add(one.interest);
        one.bankCode = bank.name();
        return one;
    }

    public static BillDetail getInstance(BigDecimal amountPerStage, BankEnum bank) {
        return getInstance(amountPerStage, 1, BigDecimal.ZERO, BillDetailTypeEnum.MERGE, bank);
    }
}
