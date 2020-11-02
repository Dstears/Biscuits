package com.mbiscuit.core.bill.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Entity
public class BillLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long billSummaryId;

    private Integer type;

    private BigDecimal amount;


    public static BillLoan getInstance(BigDecimal amount, LoanEnum type) {
        BillLoan one = new BillLoan();
        one.type = type.getType();
        one.amount = amount;
        return one;
    }
}
