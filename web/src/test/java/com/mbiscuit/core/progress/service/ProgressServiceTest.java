package com.mbiscuit.core.progress.service;

import com.mbiscuit.core.CoreApplicationTests;
import com.mbiscuit.core.common.pojo.PageParam;
import com.mbiscuit.core.progress.pojo.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProgressServiceTest extends CoreApplicationTests {

    @Autowired
    private ProgressService progressService;

    @Test
    void saveTodayDiary() {
        Diary diary = new Diary();
        diary.setHasProgress(0);
        progressService.saveTodayDiary(diary);
    }

    @Test
    void listDiary() {
        List<Diary> list = progressService.listDiary(PageParam.getInstance(1, 10));
        System.out.println(list);
    }
}
