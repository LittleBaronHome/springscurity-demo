package com.springsecurity.demo.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class SysUserDetailService implements UserDetailsService {
    private List<String> users = Arrays.asList(
            "admin:$2a$10$prNo0MoUaJBPT6JfFWLTwuZwTP6NOycqWHB7g0D2IiY9/7RPcpe36:ROLE_ADMIN", // 12345
            "user:$2a$10$prNo0MoUaJBPT6JfFWLTwuZwTP6NOycqWHB7g0D2IiY9/7RPcpe36:ROLE_USER"
    );
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<String> user_list = users.stream().filter(item -> s.equals(item.split(":")[0])).collect(Collectors.toList());
        if (user_list.size() == 0) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        String user = user_list.get(0);
        return new SysUser(
                AuthorityUtils.createAuthorityList(user.split(":")[2]),
                user.split(":")[1],
                user.split(":")[0],
                true,
                true,
                true,
                true
        );
    }
}
