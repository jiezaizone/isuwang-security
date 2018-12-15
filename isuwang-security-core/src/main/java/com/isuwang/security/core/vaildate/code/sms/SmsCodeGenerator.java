package com.isuwang.security.core.vaildate.code.sms;

import com.isuwang.security.core.properties.SecurityProperties;
import com.isuwang.security.core.vaildate.code.ValidateCode;
import com.isuwang.security.core.vaildate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator(ServletWebRequest request) {

        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code,securityProperties.getCode().getSms().getExpired());
    }


}
