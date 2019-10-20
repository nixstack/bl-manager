package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBaseDao extends JpaRepository<CourseBase, String> {
    CourseBase findByName(String name);
}
