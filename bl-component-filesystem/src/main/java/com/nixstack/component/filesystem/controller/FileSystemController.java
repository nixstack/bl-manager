package com.nixstack.component.filesystem.controller;

import com.nixstack.base.api.filesystem.IFileSystemControllerApi;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.filesystem.entity.FileSystem;
import com.nixstack.component.filesystem.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// 如果前端不使用代理，需加CrossOrigin注解才能解决跨哉
@CrossOrigin
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements IFileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;

    @PostMapping("/upload")
    public ResultRes<FileSystem> upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata) {
        return fileSystemService.upload(multipartFile, filetag, businesskey, metadata);
    }
}
