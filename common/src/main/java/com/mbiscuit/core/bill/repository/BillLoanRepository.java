package com.mbiscuit.core.bill.repository;

import com.mbiscuit.core.bill.pojo.BillLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillLoanRepository extends JpaRepository<BillLoan, Long> {

    List<BillLoan> findAllByBillSummaryId(Long billSummaryId);

    void deleteAllByBillSummaryId(Long billSummaryId);
}
