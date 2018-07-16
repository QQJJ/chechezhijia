package com.chechezhijia.controller;

import com.chechezhijia.entity.WxAuth;
import com.chechezhijia.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于验证消息的确来自微信服务器
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 14:13
 */
@Controller
@RequestMapping("core")
public class CoreController {

    @Autowired
    private CoreService coreService;

    @GetMapping
    @ResponseBody
    public String checkSignature(WxAuth wxAuth){
        return coreService.checkSignature(wxAuth);
    }

    @PostMapping
    @ResponseBody
    public String dealMessage(HttpServletRequest request){
        return coreService.dealMessage(request);
    }

}
