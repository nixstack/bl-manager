package com.nixstack.base.model.cms.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cms_server")
public class CmsServer {
    //站点id
    private String siteId;
    //服务器ID
    @Id
    private String serverId;
    //服务器IP
    private String ip;
    //端口
    private String port;
    //访问地址
    private String webPath;
    //服务器名称（代理、静态、动态、CDN）
    private String serverName;
    //资源发布地址（完整的HTTP接口）
    private String uploadPath;
    //使用类型（测试、生产）
    private String useType;
}
