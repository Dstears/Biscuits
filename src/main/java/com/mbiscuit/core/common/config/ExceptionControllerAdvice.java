package com.mbiscuit.core.common.config;

import com.mbiscuit.core.common.pojo.BasicResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public BasicResult handleException(Exception e) {
        BasicResult result = BasicResult.fail(e.getMessage());

        logger.error(e.getMessage(), e);
        return result;
    }
}
