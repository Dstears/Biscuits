package com.mbiscuit.core.common.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlConfig {

    @Bean
    public XxlJobExecutor xxlJobExecutor(){
        XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setAdminAddresses("http://localhost:8082/xxl-job-admin");
        return xxlJobExecutor;
    }
}
