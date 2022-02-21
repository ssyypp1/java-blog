package com.syp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Article;

public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();
}
