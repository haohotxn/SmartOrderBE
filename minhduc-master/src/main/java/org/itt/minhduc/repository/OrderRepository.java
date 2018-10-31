package org.itt.minhduc.repository;

import org.itt.minhduc.domain.Order;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

}
