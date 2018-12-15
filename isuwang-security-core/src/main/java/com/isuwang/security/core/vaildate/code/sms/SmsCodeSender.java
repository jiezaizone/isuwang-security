package com.isuwang.security.core.vaildate.code.sms;

/**
 * 定义短信发送接口
 */
public interface SmsCodeSender {

    void send(String mobile,String code);
}
