package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, String> {
    List<Category> findByParentid(String parentId);

    Category findByName(String name);
}
