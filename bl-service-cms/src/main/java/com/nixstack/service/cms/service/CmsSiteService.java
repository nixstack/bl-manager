package com.nixstack.service.cms.service;

import com.nixstack.base.common.code.ECmsCode;
import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.exception.CustomException;
import com.nixstack.base.common.exception.ExceptionCast;
import com.nixstack.base.common.response.PageResult;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsPage;
import com.nixstack.base.model.cms.entity.CmsSite;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.dao.CmsSiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsSiteService {
    @Autowired
    CmsSiteDao cmsSiteDao;

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
        Page<CmsSite> list = cmsSiteDao.findAll(pageable);
        PageResult pageResult = new PageResult();
        pageResult.setList(list.getContent()); // 数据列表
        pageResult.setTotal(list.getTotalElements()); // 数据总记录数

        return new PageResultRes(ECommonCode.SUCCESS, pageResult);
    }

    public ResultRes add(CmsSite cmsSite) {
        if(cmsSite == null){
            //抛出异常，非法参数异常..指定异常信息的内容
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }

        CmsSite cmsSiteBySiteName = cmsSiteDao.findCmsSiteBySiteName(cmsSite.getSiteName());

        if(cmsSiteBySiteName!=null){
            //页面已经存在
            //抛出异常，异常内容就是页面已经存在
            ExceptionCast.cast(ECommonCode.RECORD_EXISTED);
        }

        //调用dao新增页面
        cmsSite.setSiteId(null);
        CmsSite save = cmsSiteDao.save(cmsSite);
        return new ResultRes(ECommonCode.SUCCESS, save);
    }

    public ResultRes delete(String siteId) {
        Optional<CmsSite> optional = cmsSiteDao.findById(siteId);
        if (optional.isPresent()) {
            cmsSiteDao.deleteById(siteId);
            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }

    public ResultRes update(String siteId, CmsSite cmsSite) {
        Optional<CmsSite> optional = cmsSiteDao.findById(siteId);
        if (optional.isPresent()) {
            CmsSite cmsSite1 = optional.get();
            cmsSite1.setSiteName(cmsSite.getSiteName());
            cmsSite1.setSiteDomain(cmsSite.getSiteDomain());
            cmsSite1.setSitePort(cmsSite.getSitePort());
            cmsSite1.setSiteWebPath(cmsSite.getSiteWebPath());
            //siteCreateTime;
            cmsSite1.setSitePhysicalPath(cmsSite.getSitePhysicalPath());
            cmsSiteDao.save(cmsSite1);

            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }
}
