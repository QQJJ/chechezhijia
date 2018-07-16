package com.chechezhijia.service;

import com.chechezhijia.entity.Menu;
import com.chechezhijia.entity.ResultMenu;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 17:04
 */
public interface MenuService {

    String create();

    String delete();

    ResultMenu list();
}
