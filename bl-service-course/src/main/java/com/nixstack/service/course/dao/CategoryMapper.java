package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryMapper {

    //查询分类
    CategoryNode selectList();
}