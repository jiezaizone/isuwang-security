package com.isuwang.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 单点登录属性配置
 *
 * @ConfigurationProperties(prefix = "isuwang.security") 会读取application.properties配置文件"isuwang.security"前缀的选项
 */
@ConfigurationProperties(prefix = "isuwang.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidteCodeProperties code = new ValidteCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidteCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidteCodeProperties code) {
        this.code = code;
    }
}
