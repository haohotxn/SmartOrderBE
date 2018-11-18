package org.itt.minhduc.repository;

import org.itt.minhduc.domain.FileAtachment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileAtachmentRepository extends MongoRepository<FileAtachment, String> {

}
