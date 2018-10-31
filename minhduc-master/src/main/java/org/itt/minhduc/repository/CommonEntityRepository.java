package org.itt.minhduc.repository;

import org.itt.minhduc.domain.CommonEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CommonEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommonEntityRepository extends MongoRepository<CommonEntity, String> {

}
