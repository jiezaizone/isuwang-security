package com.isuwang.security.core.properties;

public class BrowserProperties {

    /**
     * 配置登录页面，并设置默认登录页面
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private String signUpUrl=SecurityConstants.DEFAULT_SIGNIN_PAGE_URL;

    /**
     * 配置登录成功返回（是页面跳转／返回JSON），默认返回JSON
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * 记住我 的时间
     */
    private int rememberMeSeconds = 3600;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
