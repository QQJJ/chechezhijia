package com.chechezhijia.utils;

import java.security.MessageDigest;

/**
 * MD5工具类
 */
public class MD5Util {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * @param
     * @return java.lang.String
     * @Title: byteArrayToHexString
     * @TitleExplain:
     * @Description: byte数组 转16进制
     * @version
     * @author wudan-mac
     */
    private static String byteArrayToHexString(byte b[]) {

        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    /**
     * @param
     * @return java.lang.String
     * @Title: byteToHexString
     * @TitleExplain:
     * @Description: byte 转16进制
     * @version
     * @author wudan-mac
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * @param
     * @return java.lang.String
     * @Title: encode
     * @TitleExplain:
     * @Description: MD5加密
     * @version
     * @author wudan-mac
     */
    public static String encode(String origin, String charsetname) {

        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    public static String encode(String origin) {
        return encode(origin, "utf-8");
    }

}
