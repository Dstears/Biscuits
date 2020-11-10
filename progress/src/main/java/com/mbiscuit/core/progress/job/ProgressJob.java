package com.mbiscuit.core.progress.job;

import com.mbiscuit.core.progress.pojo.Diary;
import com.mbiscuit.core.progress.service.ProgressService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressJob {

    @Autowired
    private ProgressService progressService;

    @XxlJob("progressAutoFail")
    public ReturnT<String> autoFail(String param) throws Exception {
        Diary diary = new Diary();
        diary.setHasProgress(0);
        progressService.saveTodayDiary(diary);
        return ReturnT.SUCCESS;
    }
}
