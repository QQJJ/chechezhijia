package com.chechezhijia.service.impl;

import com.chechezhijia.entity.WXArticleMessage;
import com.chechezhijia.entity.WXNewsMessage;
import com.chechezhijia.entity.WXTextMessage;
import com.chechezhijia.entity.WxAuth;
import com.chechezhijia.service.CoreService;
import com.chechezhijia.utils.Sha1Util;
import com.chechezhijia.utils.WXConstant;
import com.chechezhijia.utils.WXMessageUtil;
import com.chechezhijia.utils.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 14:15
 */
@Service
public class CoreServiceImpl implements CoreService {

    private static final Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);

    @Value("${weixin.token}")
    private String token;

    // 验证消息是否来自微信服务器
    @Override
    public String checkSignature(WxAuth wxAuth) {
        logger.info("--- 验证消息是否来自微信服务器 ---" + wxAuth.toString());
        String signature = wxAuth.getSignature();
        logger.info("--- signature: "+ signature);
        String timestamp = wxAuth.getTimestamp();
        String nonce = wxAuth.getNonce();
        String echostr = wxAuth.getEchostr();

        String[] arr = {token, timestamp, nonce};
        // 数组排序
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        // sha1 解密
        String sha1 = Sha1Util.getSha1(sb.toString());
        logger.info("--- sha1加密结果: "+ sha1);
        // 与微信发送的签名进行比较 相同则表示来自微信
        if(signature.equals(sha1)){
            logger.info("--- 当前消息来自微信 ---");
            return echostr;
        }
        logger.info("--- 当前消息非来自微信 ---");
        return null;
    }

    // 微信消息处理
    @Override
    public String dealMessage(HttpServletRequest request){
        logger.info("--- 进入微信消息处理 ---");
        String responStr = "回复关键字获取资源"; // 默认返回结果
        try {
            Map<String, String> map = XmlUtil.xml2Map(request);

            // 发送方帐号（open_id）
            String fromUserName = map.get("FromUserName");
            // 公众帐号
            String toUserName = map.get("ToUserName");
            // 消息类型
            String msgType = map.get("MsgType");

            //TODO 处理消息类型

            if(WXConstant.RESP_MESSAGE_TYPE_TEXT.equals(msgType)){
                logger.info("--- 消息类型: text ---");
                // 接收文本消息内容
                String content = map.get("Content");

                // 回复文本消息
                WXTextMessage textMessage = new WXTextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType(WXConstant.RESP_MESSAGE_TYPE_TEXT);
                // textMessage.setFuncFlag(0); 位0x0001被标志时，星标刚收到的消息

                // 表情内容 原样返回
                if(WXMessageUtil.isQqFace(content)){
                    logger.info("--- 表情消息 ---");
                    responStr = content;
                    textMessage.setContent(responStr);
                    return WXMessageUtil.textMessage2Xml(textMessage);
                }else{
                    logger.info("--- 文本消息 ---");
                    if("1".equals(content)){
                        responStr = "数字1";
                        textMessage.setContent(responStr);
                        return WXMessageUtil.textMessage2Xml(textMessage);
                    }else if("2".equals(content)){
                        responStr = "数字2";
                        textMessage.setContent(responStr);
                        return WXMessageUtil.textMessage2Xml(textMessage);
                    }else if("3".equals(content)){
                        responStr = "<a href = 'https://www.baidu.com/'>百度一下</a>";
                        textMessage.setContent(responStr);
                        return WXMessageUtil.textMessage2Xml(textMessage);
                    }else if("4".equals(content)){
                        // 测试发送图文消息
                        WXNewsMessage wXNewsMessage = new WXNewsMessage();
                        wXNewsMessage.setToUserName(fromUserName);
                        wXNewsMessage.setFromUserName(toUserName);
                        wXNewsMessage.setCreateTime(new Date().getTime());
                        wXNewsMessage.setMsgType(WXConstant.RESP_MESSAGE_TYPE_NEWS); // 图文

                        List<WXArticleMessage> list = new ArrayList<>();
                        WXArticleMessage wXArticleMessage = new WXArticleMessage();
                        wXArticleMessage.setDescription("测试图文");
                        wXArticleMessage.setPicUrl("https://www.baidu.com/img/bd_loggero1.png");
                        wXArticleMessage.setTitle("欢迎");
                        wXArticleMessage.setUrl("https://www.baidu.com");
                        list.add(wXArticleMessage);

                        wXNewsMessage.setArticles(list);
                        wXNewsMessage.setArticleCount(list.size());

                        return WXMessageUtil.newsMessage2Xml(wXNewsMessage);
                    }
                }
            }else if(WXConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
                // 事件
                logger.info("--- 进入事件处理 ---");
                String event = map.get("Event");
                String responstr = "";
                if(WXConstant.EVENT_TYPE_SUBSCRIBE.equals(event)){
                    // 订阅
                    logger.info("--- 用户订阅开始 ---");
                    WXTextMessage textMessage = new WXTextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setCreateTime(new Date().getTime());
                    textMessage.setMsgType(WXConstant.RESP_MESSAGE_TYPE_TEXT);
                    textMessage.setContent("欢迎订阅");
                    responstr = WXMessageUtil.textMessage2Xml(textMessage);
                    logger.info("--- 用户订阅结束 ---");

                }else if(WXConstant.EVENT_TYPE_UNSUBSCRIBE.equals(event)){
                    // 取消订阅
                    logger.info("--- 用户取消订阅:"+ fromUserName +" ---");
                }else if(WXConstant.EVENT_TYPE_CLICK.equals(event)){
                    logger.info("--- 用户点击事件 ---");
                    if("RECOMMEND_EVERY_DAY".equals(map.get("EventKey"))){
                        logger.info("--- 用户点击每日推荐 ---");
                        WXNewsMessage wXNewsMessage = new WXNewsMessage();
                        wXNewsMessage.setToUserName(fromUserName);
                        wXNewsMessage.setFromUserName(toUserName);
                        wXNewsMessage.setCreateTime(new Date().getTime());
                        wXNewsMessage.setMsgType(WXConstant.RESP_MESSAGE_TYPE_NEWS); // 图文

                        List<WXArticleMessage> list = new ArrayList<>();
                        WXArticleMessage wXArticleMessage = new WXArticleMessage();
                        wXArticleMessage.setDescription("每日推荐1");
                        wXArticleMessage.setPicUrl("https://www.baidu.com/img/bd_loggero1.png");
                        wXArticleMessage.setTitle("欢迎进行每日推荐");
                        wXArticleMessage.setUrl("https://www.baidu.com");
                        list.add(wXArticleMessage);

                        wXNewsMessage.setArticles(list);
                        wXNewsMessage.setArticleCount(list.size());

                        return WXMessageUtil.newsMessage2Xml(wXNewsMessage);
                    }
                }
                return responstr;

            }
            // TODO 其他消息类型
            return "";
        } catch (Exception ex) {
            logger.info("--- 消息转换异常 ---");
            ex.printStackTrace();
            return "";
        }
    }
}
