package com.jxtii.oa.common.utils;

import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by guolf on 17/4/17.
 */
public class RequestUtils {

    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "message";

    private static String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private static String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private static String messageParam = DEFAULT_MESSAGE_PARAM;

    public static boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    public static String getMobileLoginParam() {
        return mobileLoginParam;
    }


    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isNotBlank((remoteAddr))) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isNotBlank((remoteAddr))) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }
}
