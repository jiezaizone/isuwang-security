package com.isuwang.security.core.vaildate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认短信发送器实现
 */
public class DefaultSmsCodeSender implements SmsCodeSender{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) {
        logger.info("向手机" + mobile + "发送短信验证码：" + code);
    }
}
