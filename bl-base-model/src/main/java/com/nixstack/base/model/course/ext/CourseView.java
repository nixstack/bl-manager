package com.nixstack.base.model.course.ext;

import com.nixstack.base.model.course.entity.CourseBase;
import com.nixstack.base.model.course.entity.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
//@NoArgsConstructor // 远程调用，无参构造方法
public class CourseView implements Serializable {
    private CourseBase courseBase;
    private CoursePic coursePic;
    private TeachplanNode teachplanNode;
}
