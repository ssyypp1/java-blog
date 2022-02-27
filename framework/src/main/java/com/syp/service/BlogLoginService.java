package com.syp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.User;
import org.springframework.stereotype.Service;

public interface BlogLoginService {

    ResponseResult login(User user);
}
