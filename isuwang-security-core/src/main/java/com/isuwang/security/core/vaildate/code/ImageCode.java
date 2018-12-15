package com.isuwang.security.core.vaildate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码
 */
public class ImageCode {

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    public ImageCode(BufferedImage image, String code, int expireInt) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
    }

    /**
     * 判断验证码是否过期
     * @return
     */
    public boolean isExpired (){
        return LocalDateTime.now().isAfter(expireTime);
    }

    private BufferedImage image;

    //验证码
    private String code;

    //超时时间
    private LocalDateTime expireTime;


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
