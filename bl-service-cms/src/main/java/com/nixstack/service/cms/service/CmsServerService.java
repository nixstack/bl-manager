package com.nixstack.service.cms.service;

import com.nixstack.base.common.code.ECommonCode;
import com.nixstack.base.common.exception.ExceptionCast;
import com.nixstack.base.common.response.PageResult;
import com.nixstack.base.common.response.PageResultRes;
import com.nixstack.base.common.response.ResultRes;
import com.nixstack.base.model.cms.entity.CmsServer;
import com.nixstack.base.model.cms.entity.CmsSite;
import com.nixstack.base.model.cms.request.QueryPageReq;
import com.nixstack.service.cms.dao.CmsServerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsServerService {
    @Autowired
    CmsServerDao cmsServerDao;

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
        Page<CmsServer> list = cmsServerDao.findAll(pageable);
        PageResult pageResult = new PageResult();
        pageResult.setList(list.getContent()); // 数据列表
        pageResult.setTotal(list.getTotalElements()); // 数据总记录数

        return new PageResultRes(ECommonCode.SUCCESS, pageResult);
    }

    public ResultRes add(CmsServer cmsServer) {
        if(cmsServer== null){
            //抛出异常，非法参数异常..指定异常信息的内容
            ExceptionCast.cast(ECommonCode.INVALID_PARAM);
        }

        CmsServer cmsServerByServerName = cmsServerDao.findCmsServerByServerName(cmsServer.getServerName());

        if(cmsServerByServerName!=null){
            //页面已经存在
            //抛出异常，异常内容就是页面已经存在
            ExceptionCast.cast(ECommonCode.RECORD_EXISTED);
        }

        //调用dao新增页面
        cmsServer.setSiteId(null);
        CmsServer save = cmsServerDao.save(cmsServer);
        return new ResultRes(ECommonCode.SUCCESS, save);
    }

    public ResultRes delete(String serverId) {
        Optional<CmsServer> optional = cmsServerDao.findById(serverId);
        if (optional.isPresent()) {
            cmsServerDao.deleteById(serverId);
            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }

    public ResultRes update(String serverId, CmsServer cmsServer) {
        Optional<CmsServer> optional = cmsServerDao.findById(serverId);
        if (optional.isPresent()) {
            CmsServer cmsServer1= optional.get();
            cmsServer1.setServerName(cmsServer.getServerName());
            cmsServer1.setIp(cmsServer.getIp());
            cmsServer1.setPort(cmsServer.getPort());
            cmsServer1.setSiteId(cmsServer.getSiteId());
            cmsServer1.setUploadPath(cmsServer.getUploadPath());
            cmsServer1.setUseType(cmsServer.getUseType());
            cmsServer1.setWebPath(cmsServer.getWebPath());

            cmsServerDao.save(cmsServer1);

            return ResultRes.SUCCESS();
        }
        return ResultRes.FAIL();
    }
}
