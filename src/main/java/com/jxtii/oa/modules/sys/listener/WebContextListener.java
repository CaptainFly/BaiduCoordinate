package com.jxtii.oa.modules.sys.listener;

import com.jxtii.oa.modules.sys.service.SystemService;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (!SystemService.printKeyLoadMessage()) {
            return null;
        }
        return super.initWebApplicationContext(servletContext);
    }
}
