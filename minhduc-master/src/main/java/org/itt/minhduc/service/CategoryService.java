package org.itt.minhduc.service;

import java.util.List;
import java.util.Optional;

import org.itt.minhduc.service.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
	
   CategoryDTO save(CategoryDTO categoryDTO);
   
   void delete(String id);
   
   CategoryDTO findByNameLike(String name);
   
   Optional<CategoryDTO> findOne(String id);
   
   List<CategoryDTO> findAll();
   
}
