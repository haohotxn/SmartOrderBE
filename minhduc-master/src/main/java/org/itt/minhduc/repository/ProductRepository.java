package org.itt.minhduc.repository;

import java.util.List;

import org.itt.minhduc.domain.Product;
import org.itt.minhduc.domain.enumeration.Catalog;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
	List<Product> findByCatalogLike(Catalog catalog);
}
