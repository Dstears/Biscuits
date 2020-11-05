package com.mbiscuit.core.progress.controller;

import com.mbiscuit.core.common.pojo.BasicResult;
import com.mbiscuit.core.progress.pojo.Diary;
import com.mbiscuit.core.progress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("saveTodayDiary")
    public BasicResult saveTodayDiary(@RequestBody Diary diary) {
        progressService.saveTodayDiary(diary);
        return BasicResult.success();
    }

    @GetMapping("getTodayOrYesterdayDiary")
    public BasicResult getTodayOrYesterdayDiary() {
        Diary diary = progressService.getTodayOrYesterdayDiary();
        return BasicResult.success(diary);
    }
}
