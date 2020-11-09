package com.mbiscuit.core.bill.service;

import com.mbiscuit.core.CoreApplicationTests;
import com.mbiscuit.core.bill.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillServiceTest extends CoreApplicationTests {

    @Autowired
    private BillService billService;

    @Test
    void saveBillDetails() {
        List<BillDetail> billDetails = new ArrayList<>();
        billDetails.add(BillDetail.getInstance(new BigDecimal("791.67"), 7, new BigDecimal("125.4"), BillDetailTypeEnum.NORMAL, BankEnum.CGB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("813.83"), 6, new BigDecimal("117.19"), BillDetailTypeEnum.NORMAL, BankEnum.CGB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("1567.6"), 9, new BigDecimal("131.68"), BillDetailTypeEnum.NORMAL, BankEnum.CGB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("3825"), 5, new BigDecimal("321.3"), BillDetailTypeEnum.NORMAL, BankEnum.CGB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("684.12"), 10, new BigDecimal("57.47"), BillDetailTypeEnum.NORMAL, BankEnum.CGB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("459.38"), 6, new BigDecimal("38.59"), BillDetailTypeEnum.NORMAL, BankEnum.CGB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("4361.58"), BankEnum.CGB));

        billDetails.add(BillDetail.getInstance(new BigDecimal("3494.31"), 5, new BigDecimal("313.04"), BillDetailTypeEnum.MERGE, BankEnum.PAB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("23268.37"), BankEnum.PAB));

        billDetails.add(BillDetail.getInstance(new BigDecimal("1696.42"), 2, new BigDecimal("150.64"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("1029.17"), 5, new BigDecimal("91.39"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("300.28"), 6, new BigDecimal("26.66"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("191.03"), 7, new BigDecimal("19.51"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("160.18"), 8, new BigDecimal("16.36"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("279.99"), 9, new BigDecimal("28.59"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("283.05"), 10, new BigDecimal("21.14"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("110.75"), 11, new BigDecimal("9.83"), BillDetailTypeEnum.NORMAL, BankEnum.SPDB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("30448"), BankEnum.SPDB));

        billDetails.add(BillDetail.getInstance(new BigDecimal("1388"), 3, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("940"), 14, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("388"), 17, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("225"), 7, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("259"), 13, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("418"), 9, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("329"), 6, new BigDecimal("0"), BillDetailTypeEnum.NORMAL, BankEnum.CEB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("5318"), BankEnum.CEB));

        billDetails.add(BillDetail.getInstance(new BigDecimal("575.25"), 3, new BigDecimal("140.82"), BillDetailTypeEnum.NORMAL, BankEnum.CMB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("1643.86"), 6, new BigDecimal("121.86"), BillDetailTypeEnum.NORMAL, BankEnum.CMB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("8899.8"), BankEnum.CMB));

        billDetails.add(BillDetail.getInstance(new BigDecimal("809"), 6, new BigDecimal("57.29"), BillDetailTypeEnum.NORMAL, BankEnum.BSH));
        billDetails.add(BillDetail.getInstance(new BigDecimal("794"), 9, new BigDecimal("56.24"), BillDetailTypeEnum.NORMAL, BankEnum.BSH));
        billDetails.add(BillDetail.getInstance(new BigDecimal("7272"), BankEnum.BSH));

        billDetails.add(BillDetail.getInstance(new BigDecimal("1549.28"), 2, new BigDecimal("133.85"), BillDetailTypeEnum.NORMAL, BankEnum.BCM));
        billDetails.add(BillDetail.getInstance(new BigDecimal("11553"), BankEnum.BCM));

        billDetails.add(BillDetail.getInstance(new BigDecimal("742.95"), 5, new BigDecimal("84.25"), BillDetailTypeEnum.NORMAL, BankEnum.HXB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("3241"), BankEnum.HXB));

        billDetails.add(BillDetail.getInstance(new BigDecimal("567.08"), 2, new BigDecimal("32.66"), BillDetailTypeEnum.NORMAL, BankEnum.CCB));
        billDetails.add(BillDetail.getInstance(new BigDecimal("4982.14"), BankEnum.CCB));

        List<BillLoan> billLoans = new ArrayList<>();
        billLoans.add(BillLoan.getInstance(new BigDecimal("18437.5"), LoanEnum.CAR));

        billService.saveBillDetails(LocalDate.now(), new BigDecimal("0"),new BigDecimal("2823.44"), billDetails, billLoans);
    }

    @Test
    void calculateBill() {
        billService.calculateBill(LocalDate.now());
    }
}
