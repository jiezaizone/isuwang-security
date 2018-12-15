package com.isuwang.security.core.properties;

/**
 * 校验码属性
 */
public class ValidteCodeProperties {

    //图形验证码默认配置
    private ImageCodeProperties image = new ImageCodeProperties();

    // 短信验证码默认配置
    private SmsCodeProperties sms = new SmsCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }
}
