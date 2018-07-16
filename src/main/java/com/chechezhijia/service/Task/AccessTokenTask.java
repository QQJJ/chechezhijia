package com.chechezhijia.service.Task;

import com.alibaba.fastjson.JSON;
import com.chechezhijia.entity.AccessToken;
import com.chechezhijia.service.AccessTokenService;
import com.chechezhijia.utils.WXBussinessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 10:45
 */

@Component
public class AccessTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenTask.class);

    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定时刷新AccessToken 2分钟执行一次
     */
    //@Scheduled(cron = "0 */2 * * * ?") // 每2分钟执行一次
    @Scheduled(fixedRate = 120000) //fixedRate 以固定速率执行。以上表示每隔2分钟执行一次
    public void autoFlushAccessToken(){
        logger.info("--- 定时刷新AccessToken 开始 ---");
        // 获取key的剩余时间
        Long expire = redisTemplate.getExpire(WXBussinessConstant.ACCESS_TOKEN, TimeUnit.SECONDS);
        if(expire < 600){// 有效期小于10分钟时 刷新
            logger.info("--- AccessToken 即将过期 执行刷新 ---");
            accessTokenService.flushAccessToken();
        }
        logger.info("--- 定时刷新AccessToken 结束 ---");
    }

}
