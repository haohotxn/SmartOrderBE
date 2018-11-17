package org.itt.minhduc.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.itt.minhduc.domain.Category;
import org.itt.minhduc.domain.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
	
    @Query("{'category._id': { $eq : ?0 }}")
    Page<Product> findAll(ObjectId id, Pageable pageable);
}
