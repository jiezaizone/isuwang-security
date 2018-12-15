package com.isuwang.security.core.properties;

/**
 * 图形验证码默认配置
 */
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties(){
        this.setLength(4);
    }

    private int width = 67;

    private int height = 23;


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

}
