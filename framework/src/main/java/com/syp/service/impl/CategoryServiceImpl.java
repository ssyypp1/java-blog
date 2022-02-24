package com.syp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syp.constants.SystemConstants;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Article;
import com.syp.domain.entity.Category;
import com.syp.domain.vo.categoryVo;
import com.syp.mapper.CategoryMapper;
import com.syp.service.ArticleService;
import com.syp.service.CategoryService;
import com.syp.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult categoryList() {
        LambdaQueryWrapper<Article> query=new LambdaQueryWrapper<>();
        //状态已发布的文章
        query.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList= articleService.list(query);
        //转化为流
        Set<Long> set = articleList.stream()
                .map(Article -> Article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类
        List<Category>categories=listByIds(set);
        List<Category> list = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //vo
        List<categoryVo> res=BeanCopyUtils.copyBeanList(list, categoryVo.class);
        return ResponseResult.okResult(res);
    }
}
