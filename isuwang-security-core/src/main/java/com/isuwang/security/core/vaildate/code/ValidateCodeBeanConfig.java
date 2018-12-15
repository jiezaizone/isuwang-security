package com.isuwang.security.core.vaildate.code;

import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.sms.DefaultSmsCodeSender;
import com.isuwang.security.core.vaildate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {


    @Autowired
    private SecurityProperties securityProperties;

    /*
     * @ConditionalOnMissingBean(name="imageCodeGenerator") 解释
     * 当spring 不存在name="imageCodeGenerator" 的bean的时候，调用使用下述方法生成的imageCodeGenerator
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name="imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 注入短信发送器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
