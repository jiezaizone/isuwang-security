package com.isuwang.security.core.vaildate.code.image;

import com.isuwang.security.core.vaildate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码
 */
public class ImageCode extends ValidateCode {

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, int expireInt) {
        super(code, expireInt);
        this.image = image;
    }


    private BufferedImage image;



    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
