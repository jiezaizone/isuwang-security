package com.isuwang.code;

import com.isuwang.security.core.vaildate.code.ImageCode;
import com.isuwang.security.core.vaildate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode generator(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生产代码");
        return null;
    }
}
