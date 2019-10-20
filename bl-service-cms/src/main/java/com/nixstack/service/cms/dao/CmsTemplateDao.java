package com.nixstack.service.cms.dao;

import com.nixstack.base.model.cms.entity.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsTemplateDao extends MongoRepository<CmsTemplate,String> {
    CmsTemplate findCmsTemplateByTemplateName(String name);
}
