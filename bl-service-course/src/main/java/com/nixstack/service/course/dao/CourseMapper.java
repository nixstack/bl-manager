package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.CourseBase;
import com.nixstack.base.model.course.ext.CourseInfo;
import com.nixstack.base.model.course.request.CourseListReq;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper
@Component // Could not autowire. No beans of 'CourseMapper' type found.
public interface CourseMapper {
    CourseBase findCourseBaseById(String id);
    Page<CourseBase> findCourseList();
    //我的课程查询列表
    Page<CourseInfo> findCourseListPage(CourseListReq courseListRequest);
}
