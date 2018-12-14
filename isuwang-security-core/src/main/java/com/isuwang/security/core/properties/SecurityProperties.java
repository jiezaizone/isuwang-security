package com.isuwang.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties(prefix = "isuwang.security") 会读取application.properties配置文件"isuwang.security"前缀的选项
 */
@ConfigurationProperties(prefix = "isuwang.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
