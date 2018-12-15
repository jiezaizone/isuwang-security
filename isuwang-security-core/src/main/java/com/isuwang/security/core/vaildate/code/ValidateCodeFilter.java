package com.isuwang.security.core.vaildate.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter spring提高的工具类，保证在过滤链中每次只会被调用一次
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

//    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(StringUtils.equals("/isuwang/login",request.getRequestURI())
            && StringUtils.equalsIgnoreCase(request.getMethod(),"post")){
            try {

                validate(new ServletWebRequest(request));
            }catch (ValidateCodeExection e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);

        String codeInRequired = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"imageCode");

        if(StringUtils.isBlank(codeInRequired)){
            throw new ValidateCodeExection("验证码的值不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeExection("验证码不存在");
        }

        if(codeInSession.isExpired()){
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeExection("验证码过期");
        }

        if(!StringUtils.equals(codeInSession.getCode(),codeInRequired)){
            throw new ValidateCodeExection("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);

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
}
