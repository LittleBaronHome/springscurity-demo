package com.springsecurity.demo.config;

import com.qh.sso.Service.BasicTokenService;
import com.qh.sso.Service.PasswordTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    private UserDetailsService userDetailsService;
    private PasswordTokenService basicTokenService;

    TokenFilter(UserDetailsService userDetailsService, PasswordTokenService basicTokenService) {
        this.userDetailsService = userDetailsService;
        this.basicTokenService = basicTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader("Authorization");
            if (StringUtils.hasText(authToken)) {
                // 从自定义tokenProvider中解析用户
                String username = basicTokenService.loadInfoByToken(authToken).getData().toString();
                // 这里仍然是调用我们自定义的UserDetailsService，查库，检查用户名是否存在，
                // 如果是伪造的token,可能DB中就找不到username这个人了，抛出异常，认证失败
                UserDetails details = userDetailsService.loadUserByUsername(username);
                if (this.basicTokenService.verify(authToken).isResult()) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    // 这里还是上面见过的，存放认证信息，如果没有走这一步，下面的doFilter就会提示登录了
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            // 调用后续的Filter,如果上面的代码逻辑未能复原“session”，SecurityContext中没有想过信息，后面的流程会检测出"需要登录"
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
