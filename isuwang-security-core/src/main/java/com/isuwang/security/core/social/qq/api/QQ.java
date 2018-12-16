package com.isuwang.security.core.social.qq.api;

import java.io.IOException;

public interface QQ {

    /**
     * 获取QQ用户信息
     * @return
     * @throws IOException
     */
    QQUserInfo getUserInfo();

}
