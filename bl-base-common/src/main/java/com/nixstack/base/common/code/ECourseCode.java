package com.nixstack.base.common.code;

public enum  ECourseCode implements IResultCode {
    COURSE_DENIED_DELETE(1,31001,"删除课程失败，只允许删除本机构的课程！"),
    COURSE_PUBLISH_PERVIEWISNULL(1,31002,"还没有进行课程预览！"),
    COURSE_PUBLISH_CDETAILERROR(1,31003,"创建课程详情页面出错！"),
    COURSE_PUBLISH_COURSEIDISNULL(1,31004,"课程Id为空！"),
    COURSE_PUBLISH_VIEWERROR(1,31005,"发布课程视图出错！"),
    COURSE_PUBLISH_CREATECOURSEPUB_ERROR(1,31006,"创建课程索引信息出错！"),
    COURSE_MEDIA_URLISNULL(1,31101,"选择的媒资文件访问地址为空！"),
    COURSE_MEDIA_NAMEISNULL(1,31102,"选择的媒资文件名称为空！"),
    COURSE_GET_NOTEXISTS(1,31103,"找不到课程信息！"),
    COURSE_MEDIA_TEACHPLAN_GRADEERROR(1,31104,"只允许选择第三级的课程计划关联视频！");

    int flag;

    int code;

    String message;

    private ECourseCode(int flag, int code, String message){
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public int flag() {
        return this.flag;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
