package com.isuwang.security.core.config;

import com.isuwang.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 注入校验成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler isuwangAuthenticationSuccessHandler;

    /**
     * 注入校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler isuwangAuthenticationFailureHandler;

    public void applyPasswordAuthenticationConfig(HttpSecurity http)throws Exception{

        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM) //自定义登录请求拦截URL
                .successHandler(isuwangAuthenticationSuccessHandler)
                .failureHandler(isuwangAuthenticationFailureHandler);
    }
}
