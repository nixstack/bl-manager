package com.nixstack.base.model.course.ext;

import com.nixstack.base.model.course.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryNode extends Category {
    List<CategoryNode> children;
}
