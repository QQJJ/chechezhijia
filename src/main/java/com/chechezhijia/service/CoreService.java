package com.chechezhijia.service;

import com.chechezhijia.entity.WxAuth;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 14:15
 */
public interface CoreService {

    String checkSignature(WxAuth wxAuth);

    String dealMessage(HttpServletRequest request);
}
