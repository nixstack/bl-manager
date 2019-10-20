package com.nixstack.service.cms.controller;

import com.nixstack.base.api.cms.ICmsConfigControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.service.CmsConfigService;
import com.nixstack.base.model.cms.entity.CmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/config/model")
public class CmsConfigController implements ICmsConfigControllerApi {

    @Autowired
    CmsConfigService cmsConfigService;

    @GetMapping
    public PageResultRes<CmsConfig> findAll() {
        return this.find(-1, -1, null);
    }

    @Override
    @GetMapping("/{page}/{size}")
    public PageResultRes<CmsConfig> find(@PathVariable int page, @PathVariable  int size, QueryPageReq queryPageReq) {
        return cmsConfigService.find(page , size, queryPageReq);
    }

    @Override
    @GetMapping("/{id}")
    public ResultRes<CmsConfig> getModel(@PathVariable("id") String id) {
        return cmsConfigService.findById(id);
    }

    @Override
    @PostMapping
    public ResultRes<CmsConfig> add(@RequestBody CmsConfig cmsConfig) {
        return cmsConfigService.add(cmsConfig);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResultRes delete(@PathVariable String id) {
        return cmsConfigService.delete(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResultRes edit(@PathVariable String id, @RequestBody CmsConfig cmsConfig) {
        return cmsConfigService.update(id, cmsConfig);
    }
}
