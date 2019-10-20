package com.nixstack.base.common.code;

import com.nixstack.base.common.code.IResultCode;

public enum ECmsCode implements IResultCode {
    CMS_ADDPAGE_EXISTSNAME(1,24001,"页面名称已存在！"),
    CMS_GENERATEHTML_DATAURLISNULL(1,24002,"从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATAISNULL(1,24003,"根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEISNULL(1,24004,"页面模板为空！"),
    CMS_GENERATEHTML_HTMLISNULL(1,24005,"生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTMLERROR(1,24005,"保存静态html出错！"),
    CMS_PAGE_NOTEXISTS(1,24006,"页面不存在"),
    CMS_COURSE_PERVIEWISNULL(1,24007,"预览页面为空！");

    //操作代码
    int flag;
    //操作代码
    int code;
    //提示信息
    String message;

    private ECmsCode(int flag, int code, String message){
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public int flag() {
        return flag;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
