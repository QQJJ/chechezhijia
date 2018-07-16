package com.chechezhijia.entity;

/**
 * 微信文本消息
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 14:58
 */
public class WXTextMessage extends WXBaseMessage{

    /**
     * 文本消息内容
     */
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
