package com.nixstack.base.model.cms.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}