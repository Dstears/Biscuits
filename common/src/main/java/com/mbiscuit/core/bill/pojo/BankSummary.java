package com.mbiscuit.core.bill.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
public class BankSummary {

    private List<BillDetail> billDetails;

    private BigDecimal amount;

    private BigDecimal interest;

    private BigDecimal total;

    private String bankCode;
}
