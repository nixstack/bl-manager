package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TeachplanMapper {
    //课程计划查询
    public TeachplanNode selectList(String courseId);
}
