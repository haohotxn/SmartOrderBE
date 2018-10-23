package com.tradacompany.app.repository;

import com.tradacompany.app.domain.UserAccess;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the UserAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccessRepository extends MongoRepository<UserAccess, String> {

}
