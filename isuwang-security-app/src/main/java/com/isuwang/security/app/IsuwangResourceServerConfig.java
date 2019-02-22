package com.isuwang.security.app;

import com.isuwang.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.isuwang.security.core.properties.SecurityConstants;
import com.isuwang.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableResourceServer
public class IsuwangResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 注入security配置
     */
    @Autowired
    private SecurityProperties securityProperties;

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

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 第三方登录配置
     */
    @Autowired
    private SpringSocialConfigurer socialSecurityConfigurer;


    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

//        applyPasswordAuthenticationConfig(http);
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM) //自定义登录请求拦截URL
                .successHandler(isuwangAuthenticationSuccessHandler)
                .failureHandler(isuwangAuthenticationFailureHandler);

        http //.apply(validateCodeSecurityConfig)
//                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(socialSecurityConfigurer)
                .and()
                .authorizeRequests()  // 配置验证配置
                    .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getSignUpUrl())
                .permitAll()
                .anyRequest()  // 所有的请求都要通过验证
                .authenticated()
                .and()
                .csrf().disable();
    }
}
