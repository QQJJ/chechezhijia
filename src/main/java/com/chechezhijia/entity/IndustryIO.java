package com.chechezhijia.entity;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/4 16:24
 */
public class IndustryIO {

    /**
     * 微信所属行业1  	互联网/电子商务	1, IT科技 IT软件与服务 2, IT硬件与设备 3
     */
    private String industry_id1;
    /**
     * 微信所属行业2
     */
    private String industry_id2;

    public String getIndustry_id1() {
        return industry_id1;
    }

    public void setIndustry_id1(String industry_id1) {
        this.industry_id1 = industry_id1;
    }

    public String getIndustry_id2() {
        return industry_id2;
    }

    public void setIndustry_id2(String industry_id2) {
        this.industry_id2 = industry_id2;
    }
}
