package com.isuwang.security.browser.controller;

import com.isuwang.security.browser.controller.support.SimpleResponse;
import com.isuwang.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时候，跳转到这里
     *
     * 判断是否来自页面的认证请求，如果是，返回认证页面，如果否，返回401和错误信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/required")
    @ResponseStatus(code =HttpStatus.UNAUTHORIZED)
    public SimpleResponse requiredAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest!= null){
            String target = savedRequest.getRedirectUrl();
            logger.info("target:"+target);
            if(StringUtils.endsWithIgnoreCase(target, ".html")){

                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());

            }

        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页。");
    }
}
