package com.mbiscuit.core.bill.service.impl;

import com.mbiscuit.core.bill.pojo.*;
import com.mbiscuit.core.bill.repository.BillDetailRepository;
import com.mbiscuit.core.bill.repository.BillLoanRepository;
import com.mbiscuit.core.bill.repository.BillSummaryRepository;
import com.mbiscuit.core.bill.service.BillService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            XSSFSheet sheet = workbook.createSheet();
            XSSFRow title = createRow(sheet, 0);

            createCell(title, 0, "银行");
            createCell(title, 1, "尾号");
            createCell(title, 2, "本");
            createCell(title, 3, "利");
            createCell(title, 4, "期数");
            createCell(title, 5, "总本");
            createCell(title, 6, "总利");
            createCell(title, 7, "总额");

            int rowIndex = 1;


            logger.debug("开始查询指定日期的账单数据");
            BillSummary billSummary = billSummaryRepository.findByDate(date);
            logger.debug("账单数据查询完成");

            if (billSummary == null) {
                throw new RuntimeException("账单不存在，请先新增");
            }

            logger.debug("开始查询信用卡详细数据");
            List<BillDetail> billDetails = billDetailRepository.findAllByBillSummaryId(billSummary.getId());
            logger.debug("信用卡详细数据查询完成");

            Map<String, List<BillDetail>> billDetailMapByBank = billDetails.stream().collect(Collectors.groupingBy(BillDetail::getBankCode));
            Map<String, BankSummary> bankSummaryMap = new HashMap<>();

            List<String> backCodes = new ArrayList<>(billDetailMapByBank.keySet());

            backCodes.sort(Comparator.comparingInt(i -> BankEnum.valueOf(i).getSort()));

            for (String key : backCodes) {
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

                result.sort(Comparator.comparingInt(BillDetail::getType));

                String bankCode = result.get(0).getBankCode();
                for (int i = 0; i < result.size(); i++) {
                    XSSFRow row = createRow(sheet, rowIndex);

                    BillDetail billDetail = result.get(i);
                    if (i == 0) {
                        BankEnum bankEnum = BankEnum.valueOf(bankCode);
                        createCell(row, 0, bankEnum.getBankName());
                        createCell(row, 1, bankEnum.getEndNumber());
                    }

                    createCell(row, 2, billDetail.getAmountPerStage());
                    createCell(row, 3, billDetail.getInterestPerStage());
                    createCell(row, 4, billDetail.getStage());
                    createCell(row, 5, billDetail.getAmount());
                    createCell(row, 6, billDetail.getInterest());
                    createCell(row, 7, billDetail.getTotal());

                    rowIndex++;
                }


                BankSummary bankSummary = new BankSummary();
                bankSummary.setBillDetails(result);
                bankSummary.setBankCode(bankCode);
                bankSummary.setAmount(result.stream().map(BillDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                bankSummary.setInterest(result.stream().map(BillDetail::getInterest).reduce(BigDecimal.ZERO, BigDecimal::add));
                bankSummary.setTotal(result.stream().map(BillDetail::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

                XSSFRow backSummaryRow = createRow(sheet, rowIndex);

                createCell(backSummaryRow, 5, bankSummary.getAmount());
                createCell(backSummaryRow, 6, bankSummary.getInterest());
                createCell(backSummaryRow, 7, bankSummary.getTotal());

                rowIndex++;
                bankSummaryMap.put(key, bankSummary);
            }
            rowIndex++;
            int totalRowIndex = rowIndex;

            for (String backCode : backCodes) {
                XSSFRow bankTotal = createRow(sheet, totalRowIndex);
                createCell(bankTotal, 1, BankEnum.valueOf(backCode).getBankName());
                createCell(bankTotal, 2, bankSummaryMap.get(backCode).getTotal());
                totalRowIndex++;
            }

            logger.debug("开始计算真实数据部分");
            BigDecimal bankTotal = bankSummaryMap.values().stream().map(BankSummary::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            XSSFRow allBankTotal = createRow(sheet, totalRowIndex);
            createCell(allBankTotal, 1, "总计");
            createCell(allBankTotal, 2, bankTotal);


            logger.debug("开始查询贷款详细数据");
            List<BillLoan> billLoans = billLoanRepository.findAllByBillSummaryId(billSummary.getId());
            logger.debug("贷款详细数据查询完成");

            int loanRowIndex = rowIndex;
            for (BillLoan billLoan : billLoans) {
                XSSFRow loanRow = createRow(sheet, loanRowIndex);
                createCell(loanRow, 5, LoanEnum.valueOf(billLoan.getType()).getName());
                createCell(loanRow, 6, billLoan.getAmount());
                loanRowIndex++;
            }

            BigDecimal loanTotalAmount = billLoans.stream().map(BillLoan::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal realTotal = bankTotal.add(loanTotalAmount);

            XSSFRow loanTotal = createRow(sheet, loanRowIndex);


            createCell(loanTotal, 5, "总计");
            createCell(loanTotal, 6, loanTotalAmount);
            loanRowIndex++;
            loanRowIndex++;

            XSSFRow allSummaryLoan = createRow(sheet, loanRowIndex);

            createCell(allSummaryLoan, 5, "车贷");
            createCell(allSummaryLoan, 6, loanTotalAmount);

            loanRowIndex++;

            XSSFRow allSummaryBank = createRow(sheet, loanRowIndex);

            createCell(allSummaryBank, 5, "银行");
            createCell(allSummaryBank, 6, bankTotal);

            loanRowIndex++;

            XSSFRow allSummary = createRow(sheet, loanRowIndex);

            createCell(allSummary, 5, "总计");
            createCell(allSummary, 6, realTotal);


            loanRowIndex++;
            loanRowIndex++;

            billSummary.setRealTotal(realTotal);


            billSummaryRepository.save(billSummary);

        } finally {
            try {
                workbook.write(new FileOutputStream(new File("./" + date.toString() + ".xlsx")));
                workbook.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveBillDetails(LocalDate date, BigDecimal repayment, BigDecimal cash, List<BillDetail> billDetails, List<BillLoan> billLoans) {
        int count = billSummaryRepository.countByDate(date);
        if (count > 0) {
            throw new RuntimeException("账单已存在");
        }

        BillSummary billSummary = BillSummary.getInstance(date, repayment, cash);
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

    private void createCell(XSSFRow row, int cellIndex, Object obj) {
        row.createCell(cellIndex).setCellValue(obj.toString());
    }

    private XSSFRow createRow(XSSFSheet sheet, int rowIndex) {
        XSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }
}
