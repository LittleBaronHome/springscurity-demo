package com.springsecurity.demo.config;

import com.qh.sso.Service.BasicTokenService;
import com.qh.sso.Service.PasswordTokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenConfigAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private PasswordTokenService basicTokenService;
    private SysUserDetailService sysUserDetailService;

    TokenConfigAdapter(PasswordTokenService basicTokenService, SysUserDetailService sysUserDetailService) {
        this.basicTokenService = basicTokenService;
        this.sysUserDetailService = sysUserDetailService;
    }

    @Override
    public void configure(HttpSecurity builder) {
        TokenFilter tokenFilter = new TokenFilter(sysUserDetailService, basicTokenService);
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
