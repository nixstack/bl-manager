package com.nixstack.base.model.course.ext;

import lombok.Data;

@Data
public class CoursePublishResult {
    String previewUrl; //页面预览的url，必须得到页面id才可以拼装
}
