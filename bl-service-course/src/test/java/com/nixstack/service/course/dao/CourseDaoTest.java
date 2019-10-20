package com.nixstack.service.course.dao;

import com.nixstack.base.model.course.entity.CourseBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseDaoTest {
    @Autowired
    CourseBaseDao courseBaseDao;
    @Autowired
    CourseMapper courseMapper;

    @Test
    public void testCourseBaseDao(){
        Optional<CourseBase> optional = courseBaseDao.findById("4028858162e0bc0a0162e0bfdf1a0000");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper(){
        CourseBase courseBase = courseMapper.findCourseBaseById("4028858162e0bc0a0162e0bfdf1a0000");
        System.out.println(courseBase);

    }
}
