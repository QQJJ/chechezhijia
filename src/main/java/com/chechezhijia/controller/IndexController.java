package com.chechezhijia.controller;

import com.chechezhijia.entity.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 14:38
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/index")
    @ResponseBody
    public ResultVO toIndex(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setData("请求成功");
        return resultVO;
    }
}
