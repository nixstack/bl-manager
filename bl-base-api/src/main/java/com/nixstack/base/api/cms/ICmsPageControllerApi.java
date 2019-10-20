package com.nixstack.base.api.cms;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsPage;
import com.nixstack.base.model.cms.pojo.CmsPostPageResult;
import com.nixstack.base.model.cms.request.LinkReq;
import com.nixstack.base.model.cms.request.QueryPageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms页面管理接口", description= "页面增、删、改查")
public interface ICmsPageControllerApi {
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name ="size",value = "每页记录数",required = true,paramType ="path",dataType = "int")
    })
    public PageResultRes find(int page, int size, QueryPageReq queryPageReq);

    //新增页面
    @ApiOperation("新增页面")
    public ResultRes<CmsPage> add(CmsPage cmsPage);

    //根据页面id查询页面信息
    @ApiOperation("根据页面id查询页面信息")
    public ResultRes<CmsPage> findById(String id);
    //修改页面
    @ApiOperation("修改页面")
    public ResultRes<CmsPage> edit(String id, CmsPage cmsPage);

    //删除页面
    @ApiOperation("删除页面")
    public ResultRes delete(String id);

    @ApiOperation("关联数据模型、服务、站点")
    public ResultRes link(LinkReq linkReq);

    //发布页面
    @ApiOperation("发布页面")
    public ResultRes publish(String pageId);

    @ApiOperation("保存页面")
    public ResultRes<CmsPage> save(CmsPage cmsPage);

    @ApiOperation("一键发布页面")
    public ResultRes<CmsPostPageResult> postPageQuick(CmsPage cmsPage);
}
