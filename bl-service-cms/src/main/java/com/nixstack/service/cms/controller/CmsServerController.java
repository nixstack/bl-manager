package com.nixstack.service.cms.controller;

import com.nixstack.base.api.cms.ICmsServerControllerApi;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsServer;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.service.CmsServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/server")
public class CmsServerController implements ICmsServerControllerApi {
    @Autowired
    CmsServerService cmsServerService;

    @GetMapping()
    public PageResultRes<CmsServer> findNoArgs() {
        return this.find(-1, -1, null);
    }

    @Override
    @GetMapping("/{page}/{size}")
    public PageResultRes<CmsServer> find(@PathVariable int page, @PathVariable int size, QueryPageReq queryPageReq) {
        return cmsServerService.find(page, size, queryPageReq);
    }

    @Override
    @PostMapping
    public ResultRes<CmsServer> add(@RequestBody CmsServer cmsServer) {
        return cmsServerService.add(cmsServer);
    }

    @Override
    @DeleteMapping("/{serverId}")
    public ResultRes delete(@PathVariable String serverId) {
        return cmsServerService.delete(serverId);
    }

    @Override
    @PutMapping("/{serverId}")
    public ResultRes edit(@PathVariable String serverId, @RequestBody CmsServer cmsServer) {
        return cmsServerService.update(serverId, cmsServer);
    }
}
