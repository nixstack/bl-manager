package com.nixstack.component.search.controller;

import com.nixstack.base.api.search.ISearchControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.model.course.entity.CoursePub;
import com.nixstack.base.model.search.request.CourseSearchParam;
import com.nixstack.component.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController implements ISearchControllerApi {
    @Autowired
    SearchService searchService;

    @GetMapping("/course/{page}/{size}")
    public PageResultRes<CoursePub> list(@PathVariable int page, @PathVariable int size, CourseSearchParam courseSearchParam) {
        return searchService.list(page, size, courseSearchParam);
    }
}
