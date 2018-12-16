package com.isuwang.security.core.social.qq.connect;

import com.isuwang.security.core.social.qq.api.QQ;
import com.isuwang.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

public class QQAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {

            QQUserInfo userInfo = api.getUserInfo();
            values.setDisplayName(userInfo.getNickname()); //昵称
            values.setImageUrl(userInfo.getFigureurl_1()); //头像ur
            values.setProfileUrl(null); //个人主页
            values.setProviderUserId(userInfo.getOpenId()); //服务商的id
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

        // do nothing
    }
}
