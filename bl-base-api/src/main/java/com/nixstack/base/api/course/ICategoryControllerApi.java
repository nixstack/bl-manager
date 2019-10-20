package com.nixstack.base.api.course;

import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.course.entity.Category;
import com.nixstack.base.model.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value = "", description = "分类管理")
public interface ICategoryControllerApi {
    @ApiOperation("查询所有分类")
    public ResultRes<List<Category>> findAll();

    @ApiOperation("查询所有分类,通过mapper")
    public ResultRes<CategoryNode> findAllByMapper();

    @ApiOperation("新增分类")
    public ResultRes<Category> add(Category category);
}
