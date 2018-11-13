package org.itt.minhduc.repository;

import java.util.Optional;

import org.itt.minhduc.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByNameLike(String name);
}
