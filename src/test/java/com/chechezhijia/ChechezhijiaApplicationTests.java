package com.chechezhijia;

import com.alibaba.fastjson.JSON;
import com.chechezhijia.entity.AccessToken;
import com.chechezhijia.utils.AesException;
import com.chechezhijia.utils.WXBizMsgCrypt;
import com.chechezhijia.utils.WXBussinessConstant;
import com.chechezhijia.utils.WXUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChechezhijiaApplicationTests {

    private String appid = "oRSTcw3Yrwex81GdhxWTyANMc2Fc";
    private String encodingAesKey = "abcdfgheUDISJXLOMnbcfhswqiAUZJskcEuiposdice";
    private String token = "12345";
    private String timeStamp = "1530672365";
    private String nonce = "192230232";

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("name","jack");
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    public void uploadTest() throws IOException {

        String accessToken = (String) redisTemplate.opsForValue().get(WXBussinessConstant.ACCESS_TOKEN);

        String filePath = "C:\\Users\\admin\\Desktop\\img\\知乎.png";

        String mediaId = WXUtils.upload(filePath, accessToken, "image");
        System.out.println(mediaId);
    }

    @Test
    public void encryptTest() throws AesException {
        // 测试微信加密
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, appid);

        String xmlStr = "<xml><aaa>123456</aaa><bbb>666</bbb></xml>";
        System.out.println("加密前" + xmlStr);
        String encryptMsg = wxBizMsgCrypt.encryptMsg(xmlStr, timeStamp, nonce);
        System.out.println("加密后" + encryptMsg);

    }

    @Test
    public void decryptTest() throws AesException {
        // 测试微信解密
        String decryptStr = "<xml>\n" +
                "<Encrypt><![CDATA[D3HQvaFRi8IH0DSvgiMkeHM4yLvAdw9gQLaeRSpiNzJcofm7+ibQuW2HeDfoT2kRLjMxy9sixPuLmURSEA/J3WxlUnBtg4XsLf/aJfpDHx+fAdJgddQZQn7PtiizWu3M]]></Encrypt>\n" +
                "<MsgSignature><![CDATA[a11fd844d6142aa4936a89bcf149e08169849be3]]></MsgSignature>\n" +
                "<TimeStamp>1530672365</TimeStamp>\n" +
                "<Nonce><![CDATA[192230232]]></Nonce>\n" +
                "</xml>";
        String msgSignature = "a11fd844d6142aa4936a89bcf149e08169849be3";
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, appid);
        //String msgSignature, String timeStamp, String nonce, String postData
        System.out.println("解密前:" + decryptStr);
        String decryptMsg = wxBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, decryptStr);
        System.out.println("解密后:" + decryptMsg);

    }
}
