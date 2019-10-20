package com.nixstack.base.api.filesystem;

import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.filesystem.entity.FileSystem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value="文件管理接口",description = "文件管理接口，提供文件的增、删、改、查")
public interface IFileSystemControllerApi {
    @ApiOperation("上传文件接口")
    public ResultRes<FileSystem> upload(MultipartFile multipartFile,
                                        String filetag,
                                        String businesskey,
                                        String metadata);
}
