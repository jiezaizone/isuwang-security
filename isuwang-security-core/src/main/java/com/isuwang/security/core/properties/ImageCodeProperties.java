package com.isuwang.security.core.properties;

/**
 * 图形验证码默认配置
 */
public class ImageCodeProperties {

    private int width = 67;

    private int height = 23;

    //验证码长度
    private int length = 4;

    //默认失效时间60秒
    private int expired = 60;

    private String url = "";

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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
