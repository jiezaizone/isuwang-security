package com.isuwang.security.core.vaildate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码处理逻辑
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 生成校验码逻辑
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;
}
