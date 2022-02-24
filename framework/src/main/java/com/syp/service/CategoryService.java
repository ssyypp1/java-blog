package com.syp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Category;

public interface CategoryService extends IService<Category> {
    ResponseResult categoryList();
}
