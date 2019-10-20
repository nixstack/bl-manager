package com.nixstack.base.model.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageReq {
    @ApiModelProperty("站点id")
    private String siteId;

    @ApiModelProperty("页面id")
    private String pageId;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("页面别名")
    private String pageAliase;

    @ApiModelProperty("模版id")
    private String templateId;
}
