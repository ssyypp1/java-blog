package com.syp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.syp.domain.entity.LoginUser;
import com.syp.domain.entity.User;
import com.syp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceDeatilImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> query=new LambdaQueryWrapper<>();
        query.eq(User::getUserName,s);
        User user = userMapper.selectOne(query);
        if(Objects.isNull(user))
            throw new RuntimeException("用户名或密码错误");
        return new LoginUser(user);
    }
}
