package com.springsecurity.demo.controller;

import com.qh.sso.Service.PasswordTokenService;
import com.springsecurity.demo.config.SysUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.qh.common.Entry.*;

@RestController
public class tokenController {

    @Autowired
    private SysUserDetailService sysUserDetailService;

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Value("${QH.PublicKey}")
    private String publicKey;

    @GetMapping("/authorize")
    public Result authorize(@RequestParam String username, @RequestParam String password){
        UserDetails userDetails = sysUserDetailService.loadUserByUsername(username);
//        if (1 == 1) throw new RuntimeException("123");
        return new Result(passwordTokenService.generator(userDetails.getUsername(), publicKey).get("token"));
    }
}
