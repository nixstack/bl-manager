package com.nixstack.service.cms.dao;

import com.nixstack.base.model.cms.entity.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CmsConfigDao extends MongoRepository<CmsConfig, String> {
    Optional<CmsConfig> findByName(String name);
}
