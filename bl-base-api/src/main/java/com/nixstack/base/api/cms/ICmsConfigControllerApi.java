package com.nixstack.base.api.cms;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsConfig;
import com.nixstack.base.model.cms.request.QueryPageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface ICmsConfigControllerApi {
    @ApiOperation("根据id查询CMS配置信息")
    public ResultRes<CmsConfig> getModel(String id);

    @ApiOperation("分页查询")
    public PageResultRes<CmsConfig> find(int page, int size, QueryPageReq queryPageReq);

    @ApiOperation("新增数据模型")
    public ResultRes<CmsConfig> add(CmsConfig cmsConfig);

    @ApiOperation("删除数据模型")
    public ResultRes delete(String id);

    @ApiOperation("编辑数据模型")
    public ResultRes edit(String id, CmsConfig cmsConfig);
}