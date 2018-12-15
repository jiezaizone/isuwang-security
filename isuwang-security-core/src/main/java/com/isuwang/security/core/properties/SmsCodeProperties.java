package com.isuwang.security.core.properties;

/**
 *  短信验证码默认配置
 */
public class SmsCodeProperties {



    //验证码长度
    private int length = 6;

    //默认失效时间60秒
    private int expired = 60;

    private String url = "";


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
