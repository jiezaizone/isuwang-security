package com.isuwang.security.core.vaildate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeExection extends AuthenticationException {

    public ValidateCodeExection(String msg) {
        super(msg);
    }
}
