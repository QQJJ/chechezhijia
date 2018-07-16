package com.chechezhijia.entity;

import java.util.List;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 16:57
 */
public class Button {

    private String type;

    private String name;

    private List<Button> sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Button> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Button> sub_button) {
        this.sub_button = sub_button;
    }
}
