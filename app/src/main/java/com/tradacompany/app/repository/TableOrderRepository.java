package com.tradacompany.app.repository;

import com.tradacompany.app.domain.TableOrder;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the TableOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableOrderRepository extends MongoRepository<TableOrder, String> {

}
