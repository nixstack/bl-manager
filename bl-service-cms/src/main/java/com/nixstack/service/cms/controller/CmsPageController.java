package com.nixstack.service.cms.controller;

import com.nixstack.base.api.cms.ICmsPageControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.pojo.CmsPostPageResult;
import com.nixstack.base.model.cms.request.LinkReq;
import com.nixstack.service.cms.service.CmsPageService;
import com.nixstack.base.model.cms.entity.CmsPage;
import com.nixstack.base.model.cms.request.QueryPageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements ICmsPageControllerApi {
    @Autowired
    CmsPageService cmsPageService;

    @GetMapping("/{page}/{size}")
    public PageResultRes find(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageReq queryPageReq) {
//        PageResult<CmsPage> queryResult = new PageResult<CmsPage>();
//        List<CmsPage> list = new ArrayList<CmsPage>();
//        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageName("测试页面");
//        list.add(cmsPage);
//        queryResult.setList(list);
//        queryResult.setTotal(1);
//
//        PageResultRes queryResResult = new PageResultRes(ECommonCode.SUCCESS, queryResult);
//        return queryResResult;

        return cmsPageService.find(page, size, queryPageReq);
    }

    @Override
    @PostMapping()
    public ResultRes<CmsPage> add(@RequestBody  CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @Override
    @GetMapping("/{id}")
    public ResultRes<CmsPage> findById(@PathVariable("id") String id) {
        return cmsPageService.getByIdRes(id);
    }

    @Override
    @PutMapping("/{id}") // put方法，http 方法中put表示更新
    public ResultRes<CmsPage> edit(@PathVariable("id")String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.update(id, cmsPage);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResultRes delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }

    @Override
    @PutMapping("/link")
    public ResultRes link(@RequestBody LinkReq linkReq) {
        return cmsPageService.link(linkReq);
    }

    @Override
    @PostMapping("/publish/{pageId}")
    public ResultRes publish(@PathVariable("pageId") String pageId) {
        return cmsPageService.publish(pageId);
    }

    @Override
    @PutMapping()
    public ResultRes<CmsPage> save(@RequestBody  CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }

    @Override
    @PostMapping("/publish/quick")
    public ResultRes<CmsPostPageResult> postPageQuick(@RequestBody CmsPage cmsPage) {
        return cmsPageService.postPageQuick(cmsPage);
    }
}
