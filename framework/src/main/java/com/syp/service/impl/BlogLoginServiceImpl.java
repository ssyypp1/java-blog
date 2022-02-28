package com.syp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.LoginUser;
import com.syp.domain.entity.User;
import com.syp.domain.vo.BlogUserLoginVo;
import com.syp.domain.vo.UserInfoVo;
import com.syp.mapper.UserMapper;
import com.syp.service.BlogLoginService;
import com.syp.utils.BeanCopyUtils;
import com.syp.utils.JwtUtil;
import com.syp.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        //封装用户发过来的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //进行认证,调用UserServiceDeatil
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout(User user) {
        //获取用户id
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id=loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin"+id);
        return ResponseResult.okResult();
    }
}
