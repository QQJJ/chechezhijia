package com.chechezhijia.controller;

import com.chechezhijia.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 10:49
 */
@Controller
@RequestMapping("accessToken")
public class AccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 手动刷新AccessToken
     */
    @RequestMapping("flushAccessToken")
    @ResponseBody
    public String flushAccessToken(){
        return accessTokenService.flushAccessToken();
    }
}
