package com.nixstack.base.api.search;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.model.course.entity.CoursePub;
import com.nixstack.base.model.search.request.CourseSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "搜索服务",description = "搜索服务，根据关键字、分类、等级条件来搜索",tags = {"搜索接口"})
public interface ISearchControllerApi {
    //搜索课程信息
    @ApiOperation("课程综合搜索")
    public PageResultRes<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);
}
