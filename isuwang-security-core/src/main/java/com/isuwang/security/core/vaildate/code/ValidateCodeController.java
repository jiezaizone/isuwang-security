package com.isuwang.security.core.vaildate.code;

import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.sms.SmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String SESSION_KEY = "SESSION_IMAGE_KEY";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //图片验证码生成器
    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    //短信验证码生成器
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        logger.info("imageCode:" + imageCode.getCode());
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSms(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        logger.info("smsCode:" + smsCode.getCode());
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY, smsCode);
        String mobile = ServletRequestUtils.getStringParameter(request,"mobile");

        smsCodeSender.send(mobile, smsCode.getCode());
    }



}
