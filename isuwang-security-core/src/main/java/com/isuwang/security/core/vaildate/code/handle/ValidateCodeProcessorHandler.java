package com.isuwang.security.core.vaildate.code.handle;

import com.isuwang.security.core.vaildate.code.ValidateCodeException;
import com.isuwang.security.core.vaildate.code.ValidateCodeProcessor;
import com.isuwang.security.core.vaildate.code.enums.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 校验码处理器管理器
 *
 * @author zhailiang
 */
@Component
public class ValidateCodeProcessorHandler {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * @param type ValidateCodeType枚举
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString());
    }

    /**
     * 根据验证码类型不同，调用不同的  ValidateCodeProcessor 接口实现
     *
     * @param type 验证码类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}
