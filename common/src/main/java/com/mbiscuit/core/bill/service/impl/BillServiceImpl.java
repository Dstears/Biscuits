package com.mbiscuit.core.bill.service.impl;

import com.mbiscuit.core.bill.pojo.*;
import com.mbiscuit.core.bill.repository.BillDetailRepository;
import com.mbiscuit.core.bill.repository.BillLoanRepository;
import com.mbiscuit.core.bill.repository.BillSummaryRepository;
import com.mbiscuit.core.bill.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private BillSummaryRepository billSummaryRepository;
    @Autowired
    private BillLoanRepository billLoanRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void calculateBill(LocalDate date) {
        logger.debug("开始查询指定日期的账单数据");
        BillSummary billSummary = billSummaryRepository.findByDate(date);
        logger.debug("账单数据查询完成");

        if (billSummary == null) {
            throw new RuntimeException("账单不存在，请先新增");
        }

        logger.debug("开始查询上次的账单数据");
        BillSummary lastBillSummary = billSummaryRepository.findByDate(date.minusMonths(1));
        logger.debug("上次的账单数据查询完成");

        logger.debug("开始查询信用卡详细数据");
        List<BillDetail> billDetails = billDetailRepository.findAllByBillSummaryId(billSummary.getId());
        logger.debug("信用卡详细数据查询完成");

        logger.debug("开始查询贷款详细数据");
        List<BillLoan> billLoans = billLoanRepository.findAllByBillSummaryId(billSummary.getId());
        logger.debug("贷款详细数据查询完成");

        Map<String, List<BillDetail>> billDetailMapByBank = billDetails.stream().collect(Collectors.groupingBy(BillDetail::getBankCode));
        Map<String, BankSummary> bankSummaryMap = new HashMap<>();
        billDetailMapByBank.keySet().forEach(key -> {
            List<BillDetail> details = billDetailMapByBank.get(key);

            List<BillDetail> result = new ArrayList<>();

            BillDetail one = null;


            for (BillDetail i : details) {
                if (Objects.equals(i.getType(), BillDetailTypeEnum.NORMAL.getType())) {
                    result.add(i);
                } else {
                    BigDecimal total = i.getTotal();
                    if (one == null) {
                        one = BillDetail.getInstance(total, BankEnum.valueOf(i.getBankCode()));
                    } else {
                        one.changeAmountPerStage(one.getTotal().add(total));
                    }
                }
            }

            result.add(one);


            BankSummary bankSummary = new BankSummary();
            bankSummary.setBillDetails(result);
            bankSummary.setBankCode(result.get(0).getBankCode());
            bankSummary.setAmount(result.stream().map(BillDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            bankSummary.setInterest(result.stream().map(BillDetail::getInterest).reduce(BigDecimal.ZERO, BigDecimal::add));
            bankSummary.setTotal(result.stream().map(BillDetail::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));


            bankSummaryMap.put(key, bankSummary);
        });
        logger.debug("开始计算真实数据部分");
        BigDecimal realTotal = bankSummaryMap.values().stream().map(BankSummary::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        realTotal = realTotal.add(billLoans.stream().map(BillLoan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        BigDecimal lastRealTotal = Optional.ofNullable(lastBillSummary).map(BillSummary::getRealTotal).orElse(BigDecimal.ZERO);
        BigDecimal realActualDecrease = lastRealTotal.subtract(realTotal);
        BigDecimal realInterest = billSummary.getRepayment().subtract(realActualDecrease);

        billSummary.setRealActualDecrease(realActualDecrease);
        billSummary.setRealTotal(realTotal);
        billSummary.setRealInterest(realInterest);


        billSummaryRepository.save(billSummary);

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveBillDetails(LocalDate date, BigDecimal repayment, List<BillDetail> billDetails, List<BillLoan> billLoans) {
        int count = billSummaryRepository.countByDate(date);
        if (count > 0) {
            throw new RuntimeException("账单已存在");
        }

        BillSummary billSummary = BillSummary.getInstance(date, repayment);
        billSummaryRepository.save(billSummary);

        billDetails.forEach(i -> i.setBillSummaryId(billSummary.getId()));

        billDetailRepository.saveAll(billDetails);

        billLoans.forEach(i -> i.setBillSummaryId(billSummary.getId()));

        billLoanRepository.saveAll(billLoans);

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteBill(LocalDate date) {
        BillSummary billSummary = billSummaryRepository.findByDate(date);
        if (billSummary == null) {
            throw new RuntimeException("账单不存在");
        }

        billDetailRepository.deleteAllByBillSummaryId(billSummary.getId());

        billLoanRepository.deleteAllByBillSummaryId(billSummary.getId());

        billSummaryRepository.deleteById(billSummary.getId());
    }
}
