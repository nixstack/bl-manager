package com.nixstack.base.api.cms;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsSite;
import com.nixstack.base.model.cms.request.QueryPageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "站点管理", description= "站点增、删、改、查")
public interface ICmsSiteControllerApi {
    @ApiOperation("分页查询")
    public PageResultRes<CmsSite> find(int page, int size, QueryPageReq queryPageReq);

    @ApiOperation("新增站点")
    public ResultRes<CmsSite> add(CmsSite cmsSite);

    @ApiOperation("删除站点")
    public ResultRes delete(String siteId);

    @ApiOperation("编辑站点")
    public ResultRes edit(String siteId, CmsSite cmsSite);
}
