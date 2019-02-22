package com.isuwang.security.browser;

import com.isuwang.security.core.config.AbstractChannelSecurityConfig;
import com.isuwang.security.browser.config.ValidateCodeAuthenticationSecurityConfig;
import com.isuwang.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.isuwang.security.core.properties.SecurityConstants;
import com.isuwang.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {


    /**
     * 注入security配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeAuthenticationSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


//    @Autowired
//    private AbstractChannelSecurityConfig formAuthenticationConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 第三方登录配置
     */
    @Autowired
    private SpringSocialConfigurer socialSecurityConfigurer;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true); //启动时候系统自动创建表
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(socialSecurityConfigurer)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
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
