package com.isuwang.security.core.vaildate.code;

import com.isuwang.security.core.vaildate.code.enums.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     *
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);

}
