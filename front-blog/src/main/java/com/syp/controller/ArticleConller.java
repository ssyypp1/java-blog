package com.syp.controller;

import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Article;
import com.syp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController//responsebody 和controller的合体
@RequestMapping("/article")
public class ArticleConller {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult res=articleService.hotArticleList();
        return res;
    }
    @GetMapping("articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetails(@PathVariable("id")long id){
        return articleService.getArticleDetails(id);
    }
}
