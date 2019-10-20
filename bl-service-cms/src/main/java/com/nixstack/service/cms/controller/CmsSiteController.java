package com.nixstack.service.cms.controller;

import com.nixstack.base.api.cms.ICmsSiteControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsSite;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements ICmsSiteControllerApi {
    @Autowired
    CmsSiteService cmsSiteService;

    @GetMapping()
    public PageResultRes<CmsSite> findNoArgs() {
        return this.find(-1, -1, null);
    }

    @Override
    @GetMapping("/{page}/{size}")
    public PageResultRes<CmsSite> find(@PathVariable int page, @PathVariable int size, QueryPageReq queryPageReq) {
        return cmsSiteService.find(page, size, queryPageReq);
    }

    @Override
    @PostMapping
    public ResultRes<CmsSite> add(@RequestBody CmsSite cmsSite) {
        return cmsSiteService.add(cmsSite);
    }

    @Override
    @DeleteMapping("/{siteId}")
    public ResultRes delete(@PathVariable String siteId) {
        return cmsSiteService.delete(siteId);
    }

    @Override
    @PutMapping("/{siteId}")
    public ResultRes edit(@PathVariable String siteId, @RequestBody CmsSite cmsSite) {
        return cmsSiteService.update(siteId, cmsSite);
    }
}
