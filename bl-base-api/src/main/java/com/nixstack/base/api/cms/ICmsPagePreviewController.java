package com.nixstack.base.api.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Api(value = "页面预览接口", description= "通过页面模版和数据生成并向应html内容")
public interface ICmsPagePreviewController {
    @ApiOperation("预览页面")
    public void preview(String pageId) throws IOException;
}
