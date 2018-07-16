package com.chechezhijia.entity;

import java.util.List;

/**
 * 微信图文消息
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 18:05
 */
public class WXNewsMessage extends WXBaseMessage{

    /**
     * 图文消息条数(10条以内)
     */
    private Integer ArticleCount;
    /**
     * 多条图文消息,首个图片为大图
     */
    private List<WXArticleMessage> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<WXArticleMessage> getArticles() {
        return Articles;
    }

    public void setArticles(List<WXArticleMessage> articles) {
        Articles = articles;
    }
}
