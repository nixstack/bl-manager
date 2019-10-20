package com.nixstack.base.api.cms;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsServer;
import com.nixstack.base.model.cms.request.QueryPageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "服务管理", description= "服务增、删、改、查")
public interface ICmsServerControllerApi {
    @ApiOperation("分页查询")
    public PageResultRes<CmsServer> find(int page, int size, QueryPageReq queryPageReq);

    @ApiOperation("新增服务")
    public ResultRes<CmsServer> add(CmsServer cmsServer);

    @ApiOperation("删除服务")
    public ResultRes delete(String serverId);

    @ApiOperation("编辑服务")
    public ResultRes edit(String id, CmsServer cmsServer);
}
