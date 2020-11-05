package com.mbiscuit.core.progress.repository;

import com.mbiscuit.core.progress.pojo.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    int countByCreateDate(LocalDate createDate);

    Diary findByCreateDate(LocalDate createDate);
}
