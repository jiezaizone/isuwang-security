package com.isuwang.security.core.vaildate.code.impl;

import com.isuwang.security.core.vaildate.code.ValidateCode;
import com.isuwang.security.core.vaildate.code.ValidateCodeGenerator;
import com.isuwang.security.core.vaildate.code.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;


/**
 * 抽象的验证码处理器
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     *  操作session 工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

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
        return (C) validateCodeGenerator.generator(request);
    }

    /**
     * 保存验证码到session
     *
     * @param request
     * @param validateCode
     */
    protected void save(ServletWebRequest request, C validateCode) {
//        validateCodeRepository.save(request, validateCode, getValidateCodeType(request));
        sessionStrategy.setAttribute(request,SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(),validateCode);
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


}
