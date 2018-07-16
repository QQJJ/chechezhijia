package com.chechezhijia.service.impl;

import com.alibaba.fastjson.JSON;
import com.chechezhijia.entity.*;
import com.chechezhijia.service.MenuService;
import com.chechezhijia.utils.HttpClientUtil;
import com.chechezhijia.utils.WXBussinessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 17:04
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String create() {
        logger.info(" 微信菜单创建开始 ");

        Menu menu = new Menu();

        ClickButton b1 = new ClickButton();
        b1.setName("每日推荐");
        b1.setType("click");
        b1.setKey("RECOMMEND_EVERY_DAY");

        Button b2 = new Button();
        b2.setName("搜索一下");
        List<Button> buts1 = new ArrayList<>();
        ViewButton v1 = new ViewButton();
        v1.setName("百度一下");
        v1.setType("view");
        v1.setUrl("https://www.baidu.com/");
        ViewButton v2 = new ViewButton();
        v2.setName("搜狗搜索");
        v2.setType("view");
        v2.setUrl("https://www.sogou.com/");
        ViewButton v3 = new ViewButton();
        v3.setName("360搜索");
        v3.setType("view");
        v3.setUrl("https://www.so.com");

        buts1.add(v1);
        buts1.add(v2);
        buts1.add(v3);
        b2.setSub_button(buts1);

        Button b3 = new Button();
        b3.setName("关于我们");
        List<Button> buts2 = new ArrayList<>();
        ViewButton v4 = new ViewButton();
        v4.setName("百度一下");
        v4.setType("view");
        v4.setUrl("https://www.baidu.com/");
        ViewButton v5 = new ViewButton();
        v5.setName("搜狗搜索");
        v5.setType("view");
        v5.setUrl("https://www.sogou.com/");
        ViewButton v6 = new ViewButton();
        v6.setName("360搜索");
        v6.setType("view");
        v6.setUrl("https://www.so.com");
        buts2.add(v4);
        buts2.add(v5);
        buts2.add(v6);
        b3.setSub_button(buts2);

        List<Button> buttons = new ArrayList<>();
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);

        menu.setButton(buttons);

        String accessToken = (String) redisTemplate.opsForValue().get(WXBussinessConstant.ACCESS_TOKEN);

        String urlStr = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        String jsonString = JSON.toJSONString(menu);

        jsonString = HttpClientUtil.doPostJson(urlStr, jsonString);

        WXResult wxResult = JSON.parseObject(jsonString, WXResult.class);
        logger.info(" 微信菜单创建开始 ");
        logger.info(" 微信菜单创建结果: " + jsonString);
        if(wxResult.getErrcode().equals(0)){
            return "success";
        }else{
            return "fail";
        }
    }

    @Override
    public ResultMenu list() {
        logger.info(" 获取微信菜单 ");
        String accessToken = (String) redisTemplate.opsForValue().get(WXBussinessConstant.ACCESS_TOKEN);
        String urlStr = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken;
        String menuJson = HttpClientUtil.doGet(urlStr);
        logger.info(" 获取微信结果: " +  menuJson);
        ResultMenu menu = JSON.parseObject(menuJson, ResultMenu.class);
        return menu;
    }


    @Override
    public String delete() {
        logger.info(" 删除微信菜单开始 ");
        String accessToken = (String) redisTemplate.opsForValue().get(WXBussinessConstant.ACCESS_TOKEN);
        String urlStr = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken;
        logger.info(" 执行删除 ");
        String jsonString = HttpClientUtil.doGet(urlStr);
        logger.info(" 删除结果: " + jsonString);
        WXResult wxResult = JSON.parseObject(jsonString, WXResult.class);
        if(wxResult.getErrcode().equals(0)){
            return "success";
        }else{
            return "fail";
        }
    }
}
