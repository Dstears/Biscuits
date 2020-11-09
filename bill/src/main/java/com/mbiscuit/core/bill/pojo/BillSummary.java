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
    private BigDecimal cash;

    private BigDecimal realTotal;


    public static BillSummary getInstance(LocalDate date, BigDecimal repayment, BigDecimal cash) {
        BillSummary one = new BillSummary();
        one.date = date;
        one.repayment = repayment;
        one.cash = cash;

        one.setRealTotal(BigDecimal.ZERO);

        return one;
    }
}
