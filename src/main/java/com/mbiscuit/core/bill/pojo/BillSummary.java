package com.mbiscuit.core.bill.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
public class BillSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal repayment;
    private LocalDate date;

    private BigDecimal total;
    private BigDecimal realTotal;

    private BigDecimal actualDecrease;
    private BigDecimal realActualDecrease;


    private BigDecimal interest;
    private BigDecimal realInterest;


    public static BillSummary getInstance(LocalDate date, BigDecimal repayment) {
        BillSummary one = new BillSummary();
        one.date = date;
        one.repayment = repayment;

        one.setRealTotal(BigDecimal.ZERO);
        one.setTotal(BigDecimal.ZERO);

        one.setActualDecrease(BigDecimal.ZERO);
        one.setRealActualDecrease(BigDecimal.ZERO);

        one.setInterest(BigDecimal.ZERO);
        one.setRealInterest(BigDecimal.ZERO);
        return one;
    }
}
