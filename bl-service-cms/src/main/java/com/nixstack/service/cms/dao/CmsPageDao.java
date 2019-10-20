package com.nixstack.service.cms.dao;

import com.nixstack.base.model.cms.entity.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageDao extends MongoRepository<CmsPage, String> {
    // 根据页面名称查询
    CmsPage findByPageName(String pageName);

    //根据页面名称、站点Id、页面webpath查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
