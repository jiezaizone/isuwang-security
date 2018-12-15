package com.isuwang.security.browser;

import com.isuwang.security.browser.authentication.IsuwangAuthenticationSuccessHandler;
import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.ValidateCodeController;
import com.isuwang.security.core.vaildate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


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

    /**
     * 配置一个密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        // 定义要添加的拦截器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(isuwangAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet(); //调用初始化方法


        http.addFilterBefore(validateCodeFilter , UsernamePasswordAuthenticationFilter.class) //在用户密码校验之前加入自定义（验证码校验拦截器）拦截器
                .formLogin()  // 配置表单访问
                .loginPage("/authentication/required")
                .loginProcessingUrl("/isuwang/login")
                .successHandler(isuwangAuthenticationSuccessHandler)
                .failureHandler(isuwangAuthenticationFailureHandler)
                .and()
                .authorizeRequests()  // 配置验证配置
                .antMatchers("/authentication/required",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/image")
                .permitAll()
                .anyRequest()  // 所有的请求都要通过验证
                .authenticated()
                .and()
                .csrf().disable();

    }
}
