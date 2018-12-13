package com.isuwang.security.core.properties.com.isuwang.security.core;

import com.isuwang.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 注解为配置类
 * @EnableConfigurationProperties 让带有@ConfigurationProperties 注解的类生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {



}
