package com.isuwang.security.core.properties;

public class BrowserProperties {

    /**
     * 配置登录页面，并设置默认登录页面
     */
    private String loginPage = "/isuwang-login.html";

    /**
     * 配置登录成功返回（是页面跳转／返回JSON），默认返回JSON
     */
    private LoginType loginType = LoginType.JSON;

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
}
