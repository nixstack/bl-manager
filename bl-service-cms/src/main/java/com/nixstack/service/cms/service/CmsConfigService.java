package com.nixstack.service.cms.service;

import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.exception.ExceptionCast;
import com.nixstack.base.common.response.PageResult;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsConfig;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.dao.CmsConfigDao;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsConfigService {
    @Autowired
    CmsConfigDao cmsConfigDao;

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
        Page<CmsConfig> list = cmsConfigDao.findAll(pageable);
        PageResult pageResult = new PageResult();
        pageResult.setList(list.getContent()); // 数据列表
        pageResult.setTotal(list.getTotalElements()); // 数据总记录数

        return new PageResultRes(ECommonCode.SUCCESS, pageResult);
    }

    public ResultRes<CmsConfig> findById(String id) {
        Optional<CmsConfig> optional = cmsConfigDao.findById(id);
        if(optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            return new ResultRes(ECommonCode.SUCCESS, cmsConfig);
        }
        return ResultRes.FAIL();
    }

    public ResultRes add(CmsConfig cmsConfig) {
        if(cmsConfig == null){
            //抛出异常，非法参数异常..指定异常信息的内容
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }

        Optional<CmsConfig> optionalCmsConfig = cmsConfigDao.findByName(cmsConfig.getName());

        if(optionalCmsConfig.isPresent()){
            //页面已经存在
            //抛出异常，异常内容就是页面已经存在
            ExceptionCast.cast(ECommonCode.RECORD_EXISTED);
        }

        cmsConfig.setId(null);
        CmsConfig save = cmsConfigDao.save(cmsConfig);
        return new ResultRes(ECommonCode.SUCCESS, save);
    }

    public ResultRes delete(String id) {
        Optional<CmsConfig> optional = cmsConfigDao.findById(id);
        if (optional.isPresent()) {
            cmsConfigDao.deleteById(id);
            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }

    public ResultRes update(String id, CmsConfig cmsConfig) {
        Optional<CmsConfig> optional = cmsConfigDao.findById(id);
        if (optional.isPresent()) {
            CmsConfig cmsConfig1 = optional.get();
            cmsConfig1.setName(cmsConfig.getName());
            cmsConfig1.setModel(cmsConfig.getModel());

            cmsConfigDao.save(cmsConfig1);

            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }
}
