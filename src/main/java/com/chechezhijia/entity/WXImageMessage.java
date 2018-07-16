package com.chechezhijia.entity;

/**
 * 微信图片消息
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 15:28
 */
public class WXImageMessage extends WXBaseMessage{

    /**
     * 图片链接（由系统生成）
     */
    private String PicUrl;
    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private Long MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public Long getMediaId() {
        return MediaId;
    }

    public void setMediaId(Long mediaId) {
        MediaId = mediaId;
    }
}
