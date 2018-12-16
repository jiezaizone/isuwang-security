package com.isuwang.security.core.vaildate.code.impl;

import com.isuwang.security.core.vaildate.code.*;
import com.isuwang.security.core.vaildate.code.enums.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;


/**
 * 抽象的验证码处理器
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *  操作session 工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 获取对应validateCodeGenerator实现类生成验证码
     *
     * @param request
     * @return
     * @throws Exception
     */
    private C generate(ServletWebRequest request) throws Exception {
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        C validateCode = (C) validateCodeGenerator.generator(request);
        logger.info("generate code:" + validateCode.getCode());
        return validateCode;
    }

    /**
     * 保存验证码到session
     *
     * @param request
     * @param validateCode
     */
    protected void save(ServletWebRequest request, C validateCode) {
        validateCodeRepository.save(request, validateCode, getValidateCodeType(request));
//        sessionStrategy.setAttribute(request,SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(),validateCode);
    }

    /**
     * 发送验证码抽象方法，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        // 截取当前具体类的前缀SimpleName， 抽象类的实现类
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidateCodeProcess");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType codeType = getValidateCodeType(request);
        //获取session中的验证码
        C codeInSession = (C) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            // 获取request携带的验证码
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            validateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }
        // 成功则删除session validateCode
        validateCodeRepository.remove(request, codeType);
    }


}
