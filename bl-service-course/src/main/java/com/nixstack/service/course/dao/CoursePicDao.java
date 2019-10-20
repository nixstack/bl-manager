package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePicDao extends JpaRepository<CoursePic, String> {
    //当返回值大于0，表示删除成功的记录数
    long deleteByCourseid(String courseId);
}
