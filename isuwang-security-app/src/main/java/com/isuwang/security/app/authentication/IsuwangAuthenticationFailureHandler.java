package com.isuwang.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isuwang.security.core.properties.LoginType;
import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现该接口，定义security 校验失败后的操作
 *
 * Authentication 实体封装有认证信息
 */
@Component("isuwangAuthenticationFailureHandler")
public class IsuwangAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");

        /*
         * 登录返回类型是JSON，则以json返回,否则调用父类方法跳转
         */
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
