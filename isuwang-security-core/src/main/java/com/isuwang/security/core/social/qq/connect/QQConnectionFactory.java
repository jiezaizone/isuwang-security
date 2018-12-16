package com.isuwang.security.core.social.qq.connect;

import com.isuwang.security.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * QQConnectionFactory 继承OAuth2ConnectionFactory
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"
     */
    public QQConnectionFactory(String providerId, String appId, String appSecrity) {
        super(providerId, new QQServiceProvider(appId, appSecrity), new QQAdapter());
    }
}
