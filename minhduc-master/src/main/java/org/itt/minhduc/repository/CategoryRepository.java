package org.itt.minhduc.repository;

import org.itt.minhduc.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByNameLike(String name);
}
