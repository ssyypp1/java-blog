package com.syp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syp.constants.SystemConstants;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Article;
import com.syp.domain.entity.Category;
import com.syp.domain.vo.ArticleListVo;
import com.syp.domain.vo.HotArticleVo;
import com.syp.domain.vo.PageVo;
import com.syp.domain.vo.articleDetailVo;
import com.syp.mapper.ArticleMapper;
import com.syp.service.ArticleService;

import com.syp.service.CategoryService;
import com.syp.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.syp.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

@Service
public class ArtcleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
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

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> query=new LambdaQueryWrapper();
        //如果有categoryid就要查询时和传入时相同
        query.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        query.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //置顶
        query.orderByDesc(Article::getIsTop);
        //分页
        Page<Article> page=new Page<>(pageNum,pageSize);
        page(page,query);
        List<Article> articles=page.getRecords();
        for (Article article : articles) {
            Category tmp = categoryService.getById(article.getCategoryId());
            article.setCategoryName(tmp.getName());
        }
        //封装
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo=new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetails(long id) {
        //根据id查询文章
        Article article=getById(id);
        //转换成vo
        articleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, articleDetailVo.class);
        //根据分类id查询
        Long categoryId=articleDetailVo.getCategoryId();
        Category category=categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

}
