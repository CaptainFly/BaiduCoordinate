package com.jxtii.oa.common.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by guolf on 17/4/18.
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object handler,
                                              Exception ex) {

        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);
        if (viewName != null) {//JSP格式返回
            if (!(request.getHeader("accept").indexOf("application/json") > -1)) {//如果不是异步请求
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = Integer.parseInt(viewName.split("/")[1]);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                    return getModelAndView(viewName, ex, request);
                }
            } else {//JSON格式返回
                JSONObject errorJson = new JSONObject();
                if (ex.getClass().equals(UnauthorizedException.class)) {
                    errorJson.put("error", "Unauthorized");
                    errorJson.put("errno", HttpStatus.UNAUTHORIZED);
                }

                try {
                    response.setContentType("application/json");
                    response.getWriter().write(errorJson.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new ModelAndView();
            }
            return null;
        } else {
            return null;
        }
    }
}