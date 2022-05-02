package com.hongzhe.controller;

import com.hongzhe.common.lang.Result;
import com.hongzhe.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/test")
    public Object test(){
        return Result.succ(sysUserService.list());
    }
}
