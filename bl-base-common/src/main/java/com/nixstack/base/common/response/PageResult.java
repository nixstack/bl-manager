package com.nixstack.base.common.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;
}
