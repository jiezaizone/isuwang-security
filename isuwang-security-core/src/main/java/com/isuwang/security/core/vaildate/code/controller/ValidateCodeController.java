package com.isuwang.security.core.vaildate.code.controller;

import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.ValidateCodeGenerator;
import com.isuwang.security.core.vaildate.code.ValidateCodeProcessor;
import com.isuwang.security.core.vaildate.code.sms.SmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@RestController
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    public static final String SESSION_KEY = "SESSION_IMAGE_KEY";
//
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;


    /**
     * 创建验证码，根据验证码类型不同，调用不同的{@link ValidateCodeGenerator}接口实现
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/code/{type}")
    public void ImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        //String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        String name = type + "ValidateCodeProcessor";
        validateCodeProcessors.get(name).create(new ServletWebRequest(request, response));
    }



}
