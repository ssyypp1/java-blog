package com.syp.controller;

import com.syp.domain.ResponseResult;
import com.syp.domain.entity.User;
import com.syp.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        return blogLoginService.login(user);
    }
}
