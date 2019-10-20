package com.nixstack.service.cms.dao;

import com.nixstack.base.model.cms.entity.CmsServer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsServerDao extends MongoRepository<CmsServer, String> {
    CmsServer findCmsServerByServerName(String serverId);
}
