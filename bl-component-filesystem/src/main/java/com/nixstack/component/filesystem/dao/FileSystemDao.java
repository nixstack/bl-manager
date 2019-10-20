package com.nixstack.component.filesystem.dao;

import com.nixstack.base.model.filesystem.entity.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileSystemDao extends MongoRepository<FileSystem, String> {
}
