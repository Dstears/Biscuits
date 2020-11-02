package com.mbiscuit.core.bill.repository;

import com.mbiscuit.core.bill.pojo.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    List<BillDetail> findAllByBillSummaryId(Long billSummaryId);

    void deleteAllByBillSummaryId(Long billSummaryId);
}
