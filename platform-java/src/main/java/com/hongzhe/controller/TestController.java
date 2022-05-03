package com.hongzhe.controller;

import com.hongzhe.common.lang.Result;
import com.hongzhe.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test")
    public Object test(){
        return Result.succ(sysUserService.list());
    }

    @GetMapping("/test/pass")
    public Result pass(){
        String password = bCryptPasswordEncoder.encode("111111");
        boolean match = bCryptPasswordEncoder.matches("111111",password);
        System.out.println(match);
        return Result.succ(password);
    }
}
