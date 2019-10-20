package com.nixstack.service.course.controller;

import com.nixstack.base.api.course.ICourseControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.course.entity.CourseBase;
import com.nixstack.base.model.course.entity.CoursePic;
import com.nixstack.base.model.course.entity.TeachPlan;
import com.nixstack.base.model.course.ext.CoursePublishResult;
import com.nixstack.base.model.course.ext.CourseView;
import com.nixstack.base.model.course.ext.TeachplanNode;
import com.nixstack.service.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController implements ICourseControllerApi {
    @Autowired
    CourseService courseService;

    @Override
    @GetMapping
    public PageResultRes<CourseBase> findAll() {
        return courseService.findAll();
    }

    @Override
    @PostMapping("/basic")
    public ResultRes<CourseBase> addBasic(@RequestBody CourseBase courseBase) {
        return courseService.addBasic(courseBase);
    }

    @Override
    @GetMapping("/teachplan/{courseId}")
    public ResultRes<TeachplanNode> findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan")
    public ResultRes addTeachplan(@RequestBody TeachPlan teachplan) {
        return courseService.addTeachPlan(teachplan);
    }

    @Override
    @PostMapping("/pic")
    public ResultRes addCoursePic(@RequestBody CoursePic coursePic) {
        return courseService.addCoursePic(coursePic);
    }

    @Override
    @GetMapping("/pic/{courseid}")
    public ResultRes<CoursePic> findCoursPic( @PathVariable("courseid") String courseId) {
        return courseService.findCoursPic(courseId);
    }

    @Override
    @DeleteMapping("/pic/{courseId}")
    public ResultRes delCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.delCoursePic(courseId);
    }

    @Override
    @GetMapping("/view/{courseId}")
    public ResultRes<CourseView> queryCourseView(@PathVariable("courseId") String id) {
        return courseService.queryCourseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public ResultRes<CoursePublishResult> preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    @Override
    @PostMapping("/publish/{id}")
    public ResultRes<CoursePublishResult> publish(@PathVariable("id") String id) {
        return courseService.publish(id);
    }
}
