package com.isuwang.security.core.vaildate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成接口
 */
public interface ValidateCodeGenerator {


    /**
     * 生成校验码
     * @param request
     * @return
     * @throws Exception
     */
    ValidateCode generator(ServletWebRequest request) throws Exception;
}
