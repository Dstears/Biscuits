package com.mbiscuit.core.progress.service;

import com.mbiscuit.core.progress.pojo.Diary;
import com.mbiscuit.core.progress.pojo.ProgressException;
import com.mbiscuit.core.progress.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author wangxiaolei
 */
@Service
public class ProgressService {

    @Autowired
    private DiaryRepository diaryRepository;

    /**
     * 保存今日是否比昨天有进步
     *
     * @param diary
     */
    public void saveTodayDiary(Diary diary) {
        LocalDate now = LocalDate.now();
        int count = diaryRepository.countByCreateDate(now);
        if (count > 0) {
            throw new ProgressException("今日已经记录了");
        }
        diary.setCreateDate(now);
        diaryRepository.save(diary);
    }

    /**
     * 返回当天的日记，如果当天没有则返回昨天
     *
     * @return
     */
    public Diary getTodayOrYesterdayDiary() {
        LocalDate now = LocalDate.now();
        Diary diary = diaryRepository.findByCreateDate(now);
        if (diary == null) {
            diary = diaryRepository.findByCreateDate(now.minusDays(1));
        }
        return diary;
    }
}
