package com.syp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-02-26 17:15:12
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

