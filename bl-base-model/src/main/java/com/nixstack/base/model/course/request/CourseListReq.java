package com.nixstack.base.model.course.request;

import com.nixstack.base.common.request.RequestData;
import lombok.Data;

@Data
public class CourseListReq extends RequestData {
    //公司Id
    private String companyId;
}
