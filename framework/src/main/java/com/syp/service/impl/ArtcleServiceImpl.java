package com.syp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Article;
import com.syp.domain.vo.HotArticleVo;
import com.syp.mapper.ArticleMapper;
import com.syp.service.ArticleService;

import com.syp.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.syp.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

@Service
public class ArtcleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page=new Page(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleVo> articleVos=BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }
}
