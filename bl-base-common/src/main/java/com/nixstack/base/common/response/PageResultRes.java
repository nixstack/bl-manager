package com.nixstack.base.common.response;

import com.nixstack.base.common.code.IResultCode;
import lombok.Data;

/**
 * 分页数据响应，统一格式
 * @param <T>
 */
@Data
public class PageResultRes<T> extends ResultRes {
    PageResult<T> data;

    public PageResultRes(IResultCode resultCode, PageResult pageResult) {
        super(resultCode);
        this.data = pageResult;
    }
}
