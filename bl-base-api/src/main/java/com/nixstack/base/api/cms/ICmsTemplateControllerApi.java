package com.nixstack.base.api.cms;

import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsTemplate;
import com.nixstack.base.model.cms.request.QueryPageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(value = "模版管理", description= "模板增、删、改、查")
public interface ICmsTemplateControllerApi {
    @ApiOperation("分页查询")
    public PageResultRes<CmsTemplate> find(int page, int size, QueryPageReq queryPageReq);

    @ApiOperation("新增模板")
    public ResultRes add(CmsTemplate cmsTemplate, MultipartFile multipartFile) throws IOException;

    @ApiOperation("删除模板")
    public ResultRes delete(String id);

    @ApiOperation("编辑模版")
    public ResultRes edit(String id, CmsTemplate cmsTemplate, MultipartFile multipartFile) throws IOException;

    @ApiOperation("上传模板")
    public ResultRes upload(MultipartFile multipartFile, String templateId) throws IOException;
}
