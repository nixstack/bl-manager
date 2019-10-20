package com.nixstack.service.course.controller;

import com.nixstack.base.api.course.ICategoryControllerApi;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.course.entity.Category;
import com.nixstack.base.model.course.ext.CategoryNode;
import com.nixstack.service.course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController implements ICategoryControllerApi {
    @Autowired
    CategoryService categoryService;

    @Override
    @GetMapping
    public ResultRes<List<Category>> findAll() {
        return categoryService.findAll();
    }

    @Override
    @GetMapping("/bymapper")
    public ResultRes<CategoryNode> findAllByMapper() {
        return categoryService.findAllByMapper();
    }

    @Override
    @PostMapping
    public ResultRes<Category> add(@RequestBody Category category) {
        return categoryService.add(category);
    }
}
