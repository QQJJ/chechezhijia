package com.chechezhijia.service.impl;

import com.alibaba.fastjson.JSON;
import com.chechezhijia.entity.AccessToken;
import com.chechezhijia.service.AccessTokenService;
import com.chechezhijia.utils.HttpClientUtil;
import com.chechezhijia.utils.WXBussinessConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 10:50
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    @Value("${weixin.grantType}")
    private String grantType;
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.secret}")
    private String secret;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String flushAccessToken() {
        logger.info("--- 执行刷新AccessToken ---");
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> map = new HashMap<>();
        map.put(WXBussinessConstant.GRANT_TYPE, grantType);
        map.put(WXBussinessConstant.APPID, appid);
        map.put(WXBussinessConstant.SECRET, secret);

        String accessTokenJson = "";
        try {
            logger.info("--- 请求获取AccessToken ---");
            accessTokenJson = HttpClientUtil.doGet(url, map);
            logger.info("--- 获取AccessToken结果: "+ accessTokenJson);
            if(StringUtils.isBlank(accessTokenJson)){
                logger.info("--- 请求获取AccessToken 无响应数据 ---");
            }else{
                // json转对象
                AccessToken accessToken = JSON.parseObject(accessTokenJson, AccessToken.class);
                String accessTokenStr = accessToken.getAccess_token();
                if(StringUtils.isBlank(accessTokenStr)){
                    logger.info("--- 请求获取AccessToken 失败 ---");
                    logger.info("--- 微信错误码: "+ accessToken.getErrcode() + " 错误信息: " + accessToken.getErrmsg());
                }else{ // 将accesstoken 存入redis
                    redisTemplate.opsForValue().set(WXBussinessConstant.ACCESS_TOKEN, accessTokenStr, accessToken.getExpires_in() - 600, TimeUnit.SECONDS);
                    accessTokenJson = accessTokenStr;
                }
            }
        } catch (Exception e) {
            logger.info("--- AccessToken 处理异常---");
            e.printStackTrace();
        }
        return accessTokenJson;
    }
}
