package com.mbiscuit.core.progress.controller;

import com.mbiscuit.core.common.pojo.BasicResult;
import com.mbiscuit.core.common.pojo.PageParam;
import com.mbiscuit.core.progress.pojo.Diary;
import com.mbiscuit.core.progress.pojo.DiaryDTO;
import com.mbiscuit.core.progress.service.ProgressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("saveTodayDiary")
    public BasicResult saveTodayDiary(@RequestBody DiaryDTO param) {
        Diary diary = new Diary();
        BeanUtils.copyProperties(param, diary);
        progressService.saveTodayDiary(diary);
        return BasicResult.success();
    }

    @GetMapping("getTodayOrYesterdayDiary")
    public BasicResult<DiaryDTO> getTodayOrYesterdayDiary() {
        DiaryDTO diary = progressService.getTodayOrYesterdayDiary();
        return BasicResult.success(diary);
    }

    @PostMapping("listDiary")
    public BasicResult<List<Diary>> listDiary(@RequestBody PageParam pageParam) {
        List<Diary> list = progressService.listDiary(pageParam);
        return BasicResult.success(list);
    }
}
