package com.nixstack.base.model.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LinkReq {
    @ApiModelProperty("页面id")
    private String pageId;

    @ApiModelProperty("数据模型id")
    private String configId;

    @ApiModelProperty("服务id")
    private String serverId;

    @ApiModelProperty("数据模型id")
    private String siteId;
}
