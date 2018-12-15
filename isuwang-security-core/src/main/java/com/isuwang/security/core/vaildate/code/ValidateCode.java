package com.isuwang.security.core.vaildate.code;

import java.time.LocalDateTime;

/**
 * 短信验证码
 */
public class ValidateCode {

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public ValidateCode(String code, int expireInt) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
    }

    /**
     * 判断验证码是否过期
     * @return
     */
    public boolean isExpired (){
        return LocalDateTime.now().isAfter(expireTime);
    }


    //验证码
    private String code;

    //超时时间
    private LocalDateTime expireTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
