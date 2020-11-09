package com.mbiscuit.core.bill.service;

import com.mbiscuit.core.bill.pojo.BillDetail;
import com.mbiscuit.core.bill.pojo.BillLoan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BillService {

    void calculateBill(LocalDate date);

    void saveBillDetails(LocalDate date, BigDecimal repayment,BigDecimal cash, List<BillDetail> billDetails, List<BillLoan> billLoans);

    void deleteBill(LocalDate date);
}
