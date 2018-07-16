package com.chechezhijia.entity;

/**
 * 微信语音消息
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 15:30
 */
public class WXVoiceMessage extends WXBaseMessage{

    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String MediaId;
    /**
     * 语音格式，如amr，speex等
     */
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
