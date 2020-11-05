package com.mbiscuit.core.progress.service;

import com.mbiscuit.core.common.pojo.PageParam;
import com.mbiscuit.core.progress.pojo.Diary;
import com.mbiscuit.core.progress.pojo.DiaryDTO;
import com.mbiscuit.core.progress.pojo.ProgressException;
import com.mbiscuit.core.progress.repository.DiaryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public DiaryDTO getTodayOrYesterdayDiary() {
        LocalDate now = LocalDate.now();
        Diary diary = diaryRepository.findByCreateDate(now);
        DiaryDTO result = new DiaryDTO();
        if (diary == null) {
            result.setIsToday(0);
            diary = diaryRepository.findByCreateDate(now.minusDays(1));
        } else {
            result.setIsToday(1);
        }
        BeanUtils.copyProperties(diary, result);
        return result;
    }

    public List<Diary> listDiary(PageParam pageParam) {

        Page<Diary> all = diaryRepository.findAll(pageParam.getPageable(Sort.by("createDate").descending()));

        return all.get().collect(Collectors.toList());

    }
}
