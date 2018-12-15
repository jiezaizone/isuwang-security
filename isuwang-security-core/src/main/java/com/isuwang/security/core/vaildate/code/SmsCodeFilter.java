package com.isuwang.security.core.vaildate.code;

import com.isuwang.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * OncePerRequestFilter spring提高的工具类，保证在过滤链中每次只会被调用一次
 */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    //登录失败处理器
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls  =  StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(),",");
        for(String configUrl :configUrls){
            urls.add(configUrl);
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 判断请求是否需要进行验证码校验
        boolean action = false;
        for (String url :urls){
            if(pathMatcher.match(url, request.getRequestURI())){
                action = true;
            }
        }

        if(action){

            try {

                validate(new ServletWebRequest(request));
            }catch (ValidateCodeExection e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    /**
     * 验证码校验
     * @param request
     * @throws ServletRequestBindingException
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");

        String codeInRequired = "";

        try{
            codeInRequired = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"smsCode");
        }catch (MissingServletRequestParameterException e){
            logger.info(e.getMessage());
        }


        if(StringUtils.isBlank(codeInRequired)){
            throw new ValidateCodeExection("验证码的值不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeExection("验证码不存在");
        }

        if(codeInSession.isExpired()){
            sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
            throw new ValidateCodeExection("验证码过期");
        }

        if(!StringUtils.equals(codeInSession.getCode(),codeInRequired)){
            throw new ValidateCodeExection("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");

    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
