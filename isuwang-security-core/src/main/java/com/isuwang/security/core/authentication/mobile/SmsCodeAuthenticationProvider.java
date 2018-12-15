package com.isuwang.security.core.authentication .mobile;

import com.isuwang.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录验证逻辑提供者
 * 由于短信验证码的验证在过滤器里已完成，这里直接读取用户信息即可。
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 返回认证结果
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        /**
         * 获取系统用户信息
         */
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationTokenResult.setDetails(authenticationToken.getDetails());
        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
