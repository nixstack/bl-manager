package com.nixstack.base.api.course;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.course.entity.CourseBase;
import com.nixstack.base.model.course.entity.CoursePic;
import com.nixstack.base.model.course.entity.TeachPlan;
import com.nixstack.base.model.course.ext.CoursePublishResult;
import com.nixstack.base.model.course.ext.CourseView;
import com.nixstack.base.model.course.ext.TeachplanNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "", description = "课程管理")
public interface ICourseControllerApi {
    @ApiOperation("查询所有课程")
    public PageResultRes<CourseBase> findAll();

//    @ApiOperation("分页查询")
//    public PageResultRes<CourseBase> find();

    @ApiOperation("新增课程基本信息")
    public ResultRes<CourseBase> addBasic(CourseBase courseBase);

    @ApiOperation("课程计划,根据课程id查询")
    public ResultRes<TeachplanNode> findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    public ResultRes addTeachplan(TeachPlan teachplan);

    @ApiOperation("添加课程图片")
    public ResultRes addCoursePic(CoursePic coursePic);

    @ApiOperation("查询课程图片")
    public ResultRes<CoursePic> findCoursPic(String courseId);

    @ApiOperation("删除课程图片")
    public ResultRes delCoursePic(String courseId);

    @ApiOperation("课程视图查询")
    public ResultRes<CourseView> queryCourseView(String id);

    @ApiOperation("预览课程")
    public ResultRes<CoursePublishResult> preview(String id);


    @ApiOperation("课程发布")
    public ResultRes<CoursePublishResult> publish(String id);
}
