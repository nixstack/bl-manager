package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMarketDao extends JpaRepository<CourseMarket, String> {
}
