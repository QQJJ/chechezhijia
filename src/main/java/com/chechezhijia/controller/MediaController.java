package com.chechezhijia.controller;

import com.chechezhijia.utils.WXUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 15:53
 */
@Controller
@RequestMapping("media")
public class MediaController {

    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {

        //String mediaId = WXUtils.upload("", WXUtils.getAccessToken(), "image");
        //System.out.println(mediaId);
        return "success";
    }
}
