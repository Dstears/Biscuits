package com.mbiscuit.core.bill.repository;

import com.mbiscuit.core.bill.pojo.BillSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BillSummaryRepository extends JpaRepository<BillSummary, Long> {

    BillSummary findByDate(LocalDate date);

    int countByDate(LocalDate date);
}
