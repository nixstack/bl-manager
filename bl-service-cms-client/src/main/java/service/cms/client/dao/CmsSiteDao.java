package service.cms.client.dao;

import com.nixstack.base.model.cms.entity.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsSiteDao extends MongoRepository<CmsSite, String> {
}
