package com.mbiscuit.core.oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("oauth")
public class OauthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${loginUrl}")
    private String loginUrl;

    @RequestMapping(value = "authorize", method = {RequestMethod.POST, RequestMethod.GET})
    public void authorize(
            @RequestParam String appid
            , @RequestParam String redirectUri
            , @RequestParam String state
            , HttpServletResponse response) {
        try {
            response.sendRedirect(loginUrl);
        } catch (IOException e) {
            logger.error("指定的重定向地址错误", e);
        }
    }
}
