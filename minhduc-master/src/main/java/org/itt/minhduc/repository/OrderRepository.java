package org.itt.minhduc.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.itt.minhduc.domain.Order;
import org.itt.minhduc.domain.enumeration.StatusOrder;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
	List<Order> findByTableIdLike(String tableId);
	
//	@Query("{'created_date':{ $gt: ?0, $lt: ?1}}")
	List<Order> findAllByCreatedDateBetween(Instant from, Instant to);
	
	List<Order> findAllByStatusLike(StatusOrder status);
}
