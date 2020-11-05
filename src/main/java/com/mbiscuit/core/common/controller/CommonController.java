package com.mbiscuit.core.common.controller;

import com.mbiscuit.core.common.pojo.BasicResult;
import com.mbiscuit.core.common.pojo.CoreException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("hello")
    public BasicResult<String> hello() {
        return BasicResult.success("hello");
    }

}
