package com.chechezhijia.entity;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/4 16:37
 */
public class IndustryVO {

    /**
     * 帐号设置的主营行业
     */
    private IndustryDetail primary_industry;
    /**
     * 帐号设置的副营行业
     */
    private IndustryDetail secondary_industry;

    public IndustryDetail getPrimary_industry() {
        return primary_industry;
    }

    public void setPrimary_industry(IndustryDetail primary_industry) {
        this.primary_industry = primary_industry;
    }

    public IndustryDetail getSecondary_industry() {
        return secondary_industry;
    }

    public void setSecondary_industry(IndustryDetail secondary_industry) {
        this.secondary_industry = secondary_industry;
    }
}
