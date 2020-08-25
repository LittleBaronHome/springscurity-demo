package com.springsecurity.demo.config;

import com.qh.sso.Service.BasicTokenService;
import com.qh.sso.Service.PasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 开启Web安全性
// 在Spring上下文中，任何实现了WebSecurityConfigurer的bean都可以用来配置SpringSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DefaultAuthenticationFailureHandle defaultAuthenticationFailureHandle;
    @Autowired
    SysUserDetailService sysUserDetailService;
    @Autowired
    PasswordTokenService basicTokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            hasAnyAuthority(String ...) 如果用户具备给定权限中任意一个就通过
            hasAnyRole(String ... ) 如果用户具备给定角色中任意一个就通过
            hasAuthority(String) 如果用户具备给定权限就通过
            hasRole(String) 如果用户具备给定角色就通过
            hasIpAddress(String) 如果请求来自指定IP就通过
            requireInsecure() // 强制使用安全通过。重定向到https
         */
        // 按顺序匹配
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(new TokenConfigAdapter(basicTokenService, sysUserDetailService))
//                .and().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointImpl())
//                .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl())
//                .and().authorizeRequests()
//                .antMatchers("/authorize").permitAll()
//                .antMatchers("/demo1/**").authenticated()
//                .antMatchers("/demo2/**").hasAuthority("ADMIN")
//                .anyRequest().permitAll()
                ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
