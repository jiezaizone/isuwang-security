package com.isuwang.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class QQOAuth2Template extends OAuth2Template {

    Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override //把请求的标准。按照qq的。做了自定义解析
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr=getRestTemplate().postForObject(accessTokenUrl,parameters,String.class);

        logger.info("获取accessToken的响应:"+responseStr);

        String[] items=StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr,"&");

        String accessToken= StringUtils.substringAfterLast(items[0],"=");

        Long expireIn=new Long(StringUtils.substringAfterLast(items[1],"="));

        String refreshToken=StringUtils.substringAfterLast(items[2],"=");

        return new AccessGrant(accessToken,null,refreshToken,expireIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {

        RestTemplate restTemplate=super.createRestTemplate();

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;

    }

}
