package com.isuwang.security.core.vaildate.code.image;

import com.isuwang.security.core.vaildate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 图片验证码创建实现
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }

}
