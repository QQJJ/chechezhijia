package com.chechezhijia.entity;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 10:12
 */
public class Event extends WXBaseMessage{

    /**
     * 事件 订阅 subscribe 取消 unsubscribe
     */
    private String Event;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
}
