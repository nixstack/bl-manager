package com.nixstack.service.cms.service;

import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.exception.ExceptionCast;
import com.nixstack.base.common.response.PageResult;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsSite;
import com.nixstack.base.model.cms.entity.CmsTemplate;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.dao.CmsTemplateDao;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class CmsTemplateService {
    @Autowired
    CmsTemplateDao cmsTemplateDao;

    @Autowired
    GridFsTemplate gridFsTemplate;

    public PageResultRes find(int page, int size, QueryPageReq queryPageReq) {
        // 分页参数
        if (page < 0) {
            page = 0;
        }
//        page = page -1;

        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<CmsTemplate> list = cmsTemplateDao.findAll(pageable);
        PageResult pageResult = new PageResult();
        pageResult.setList(list.getContent()); // 数据列表
        pageResult.setTotal(list.getTotalElements()); // 数据总记录数

        return new PageResultRes(ECommonCode.SUCCESS, pageResult);
    }

    public ResultRes add(CmsTemplate cmsTemplate, MultipartFile multipartFile) throws IOException {
        if(cmsTemplate == null){
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }

        CmsTemplate cmsTemplateByTemplateName = cmsTemplateDao.findCmsTemplateByTemplateName(cmsTemplate.getTemplateName());

        if(cmsTemplateByTemplateName!=null){
            ExceptionCast.cast(ECommonCode.RECORD_EXISTED);
        }

        if (multipartFile != null) {
            ObjectId objectId = gridFsTemplate.store(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            if (objectId != null) {
                cmsTemplate.setTemplateFileId(objectId.toHexString());
            }
        }

        cmsTemplate.setTemplateId(null);
        CmsTemplate save = cmsTemplateDao.save(cmsTemplate);
        return new ResultRes(ECommonCode.SUCCESS, save);
    }

    public ResultRes delete(String id) {
        Optional<CmsTemplate> optional = cmsTemplateDao.findById(id);
        if (optional.isPresent()) {
            cmsTemplateDao.deleteById(id);
            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }

    public ResultRes update(String id, CmsTemplate cmsTemplate, MultipartFile multipartFile) throws IOException {
        Optional<CmsTemplate> optional = cmsTemplateDao.findById(id);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate1 = optional.get();
            cmsTemplate1.setSiteId(cmsTemplate.getSiteId());
            cmsTemplate1.setTemplateName(cmsTemplate.getTemplateName());
            cmsTemplate1.setTemplateParameter(cmsTemplate.getTemplateParameter());
            cmsTemplate1.setTemplateFileId(cmsTemplate.getTemplateFileId());

            if (multipartFile != null) {
                ObjectId objectId = gridFsTemplate.store(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
                if (objectId != null) {
                    cmsTemplate1.setTemplateFileId(objectId.toHexString());
                }
            }

            cmsTemplateDao.save(cmsTemplate1);

            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }

    public ResultRes upload(MultipartFile multipartFile, String templateId) throws IOException {
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }
        Optional<CmsTemplate> optional = cmsTemplateDao.findById(templateId);
        if(optional.isPresent()) {
            ObjectId objectId = gridFsTemplate.store(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), "");
            if (objectId != null) {
                CmsTemplate cmsTemplate = optional.get();
                cmsTemplate.setTemplateId(objectId.toHexString());
                cmsTemplateDao.save(cmsTemplate);
                return ResultRes.SUCCESS();
            }
        }

        return ResultRes.FAIL();
    }
}
