package com.chechezhijia.utils;

import com.alibaba.fastjson.JSONObject;
import com.chechezhijia.entity.IndustryIO;
import com.chechezhijia.entity.IndustryVO;
import com.chechezhijia.entity.WXResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  微信工具集
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/3 14:51
 */
public class WXUtils {

    private static final Logger logger = LoggerFactory.getLogger(WXUtils.class);


    /**
     * 微信文件上传
     * @param filePath 文件路径
     * @param accessToken 微信access_token
     * @param type 上传文件类型
     * @return 微信mediaId
     */
    public static String upload(String filePath, String accessToken, String type) throws IOException {
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            throw new IOException("文件不存在");
        }
        String urlStr = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        RedisTemplate redisTemplate = new RedisTemplate();
        String accessTokenJson = (String) redisTemplate.opsForValue().get(WXBussinessConstant.ACCESS_TOKEN);
        urlStr = urlStr.replace("ACCESS_TOKEN", accessTokenJson).replace("TYPE", type);

        URL url = new URL(urlStr);

        // 使用HttpUrlConnection 连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // 忽略缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String boundary = "------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        StringBuilder sb = new StringBuilder();
        sb.append("---");
        sb.append(boundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出头
        out.write(head);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes;
        byte[] bufferOut = new byte[1024];
        while((bytes = in.read(bufferOut))!= -1){
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");

        out.write(foot);
        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try{
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
            if(result == null){
                result = buffer.toString();
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(reader != null){
                reader.close();
            }
        }
        JSONObject jsonObj = JSONObject.parseObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if(!"image".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }


    /**
     * 获取 AccessToken 字符串
     * @return AccessToken
     */
    public static String getAccessToken(){
        RedisTemplate redisTemplate = new RedisTemplate();
        String accessTokenJson = (String) redisTemplate.opsForValue().get(WXBussinessConstant.ACCESS_TOKEN);
        return accessTokenJson;
    }

    /**
     * 设置微信所属行业
     * @return WXResult
     */
    public static WXResult editIndustry(IndustryIO industryIO){
        logger.info("--- 设置微信所属行业开始 ---");
        String parameter = JSONObject.toJSONString(industryIO);
        logger.info("--- 设置微信所属行业入参: " + parameter);
        String editIndustryUrl = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" +
                getAccessToken();
        String resultStr = HttpClientUtil.doPostJson(editIndustryUrl, parameter);
        logger.info("--- 设置微信所属行业响应: " + resultStr);
        logger.info("--- 设置微信所属行业结束 ---");
        return JSONObject.parseObject(resultStr, WXResult.class);
    }

    /**
     * 获取微信所属行业
     * @return IndustryVO
     */
    public static IndustryVO getIndustry(){
        logger.info("--- 获取微信所属行业开始 ---");
        String getIndustryUrl = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" +
                getAccessToken();
        String resultStr = HttpClientUtil.doGet(getIndustryUrl);
        logger.info("--- 获取微信所属行业响应: " + resultStr);
        logger.info("--- 获取微信所属行业结束 ---");
        return JSONObject.parseObject(resultStr, IndustryVO.class);
    }




}
