package com.jxtii.oa.common.security;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

public class HmacSHA256Utils {

    public static String digest(String key, String content) {
        try {
            System.out.println("content = " + content);
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes("utf-8");
            byte[] dataBytes = content.getBytes("utf-8");

            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            return new String(hexB, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String digest(String key, Map<String, ?> map) {
        return digest(key, JSON.toJSONString(map));
    }

    public static void main(String[] args) {
        String url = "http://10.10.61.8:8084/api/token?username=test1&password=1234";
        System.out.println("digest = " + digest("a2bf0dc7676926515eb49ba5e2294b62b6eb40c0a125c8139b0f12fa00e27d6a", url.split("[?]")[1]));
    }

}