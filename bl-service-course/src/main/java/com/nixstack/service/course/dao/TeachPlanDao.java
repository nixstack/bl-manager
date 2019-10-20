package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.TeachPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachPlanDao extends JpaRepository<TeachPlan, String> {
    //根据课程id和parentid查询teachplan，SELECT * FROM teachplan a WHERE a.courseid ='{courseId}' AND a.parentid='0'
    public List<TeachPlan> findByCourseidAndParentid(String courseId, String parentId);
}
