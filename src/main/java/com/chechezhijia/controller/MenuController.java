package com.chechezhijia.controller;

import com.chechezhijia.entity.Menu;
import com.chechezhijia.entity.ResultMenu;
import com.chechezhijia.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 17:03
 */
@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("create")
    @ResponseBody
    public String create(){
        return menuService.create();
    }

    @RequestMapping("list")
    @ResponseBody
    public ResultMenu list(){
        return menuService.list();
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(){
        return menuService.delete();
    }
}
