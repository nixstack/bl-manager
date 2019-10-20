package com.nixstack.base.common.code;

public enum EFileSystemCode implements IResultCode {
    FS_UPLOADFILE_FILEISNULL(1,25001,"上传文件为空！"),
    FS_UPLOADFILE_BUSINESSISNULL(1,25002,"业务Id为空！"),
    FS_UPLOADFILE_SERVERFAIL(1,25003,"上传文件服务器失败！"),
    FS_DELETEFILE_NOTEXISTS(1,25004,"删除的文件不存在！"),
    FS_DELETEFILE_DBFAIL(1,25005,"删除文件信息失败！"),
    FS_DELETEFILE_SERVERFAIL(1,25006,"删除文件失败！"),
    FS_UPLOADFILE_METAERROR(1,25007,"上传文件的元信息请使用json格式！"),
    FS_UPLOADFILE_USERISNULL(1,25008,"上传文件用户为空！"),
    FS_INITFDFSERROR(1,25009,"初始化fastDFS环境出错！");

    //操作代码
    int flag;

    //操作代码
    int code;
    //提示信息
    String message;

    private EFileSystemCode(int flag, int code, String message){
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
