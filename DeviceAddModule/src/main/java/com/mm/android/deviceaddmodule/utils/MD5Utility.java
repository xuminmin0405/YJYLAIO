package com.mm.android.deviceaddmodule.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utility {

    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("md5");
        StringBuilder buffer = new StringBuilder();
        byte[] result = digest.digest(val.getBytes());
        for (byte b : result) {
            int number = b & 0xff;//不按标准加密
            //转换成16进制
            String numberStr = Integer.toHexString(number);
            if (numberStr.length() == 1) {
                buffer.append("0");
            }
            buffer.append(numberStr);
        }     //MD5加密结果
        return buffer.toString().toUpperCase();
    }
}
