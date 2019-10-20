package com.nixstack.service.cms.controller;

import com.nixstack.base.api.cms.ICmsTemplateControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsTemplate;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements ICmsTemplateControllerApi {
    @Autowired
    CmsTemplateService cmsTemplateService;

    @GetMapping()
    public PageResultRes<CmsTemplate> findNoArgs() {
        return this.find(-1, -1, null);
    }

    @Override
    @GetMapping("/{page}/{size}")
    public PageResultRes<CmsTemplate> find(@PathVariable(required = false) int page, @PathVariable(required = false) int size, QueryPageReq queryPageReq) {
        return cmsTemplateService.find(page, size, queryPageReq);
    }

//    @Override
//    @PostMapping
//    public ResultRes<CmsTemplate> add(@RequestBody CmsTemplate cmsTemplate, @RequestBody MultipartFile multipartFile) throws IOException {
//        return cmsTemplateService.add(cmsTemplate, multipartFile);
//    }

    @Override
    @PostMapping
    public ResultRes<CmsTemplate> add(CmsTemplate cmsTemplate, MultipartFile multipartFile) throws IOException {
        return cmsTemplateService.add(cmsTemplate, multipartFile);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResultRes delete(@PathVariable String id) {
        return cmsTemplateService.delete(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResultRes edit(@PathVariable String id, CmsTemplate cmsTemplate, MultipartFile multipartFile) throws IOException {
        return cmsTemplateService.update(id, cmsTemplate, multipartFile);
    }

    @Override
    @PostMapping("/upload")
    public ResultRes upload(MultipartFile multipartFile, String templateId) throws IOException {
        return cmsTemplateService.upload(multipartFile, templateId);
    }
}
