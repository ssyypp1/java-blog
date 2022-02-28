package com.syp.controller;

import com.syp.domain.ResponseResult;
import com.syp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//因为这个注解responseresult会被封装成json写入响应体
@RequestMapping("/category")
public class CategoryConller {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult categoryList(){
        return categoryService.categoryList();
    }
}
