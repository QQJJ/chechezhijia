package com.chechezhijia.entity;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/4 16:36
 */
public class IndustryDetail {

    /**
     * 主行业
     */
    private String first_class;
    /**
     * 副行业
     */
    private String second_class;

    public String getFirst_class() {
        return first_class;
    }

    public void setFirst_class(String first_class) {
        this.first_class = first_class;
    }

    public String getSecond_class() {
        return second_class;
    }

    public void setSecond_class(String second_class) {
        this.second_class = second_class;
    }
}
