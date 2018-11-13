package org.itt.minhduc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.itt.minhduc.domain.Category;
import org.itt.minhduc.repository.CategoryRepository;
import org.itt.minhduc.service.CategoryService;
import org.itt.minhduc.service.dto.CategoryDTO;
import org.itt.minhduc.service.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
	
    @Autowired
    CategoryMapper categoryMapper;
    
    @Autowired
    CategoryRepository categoryRepository;
    
	@Override
	public CategoryDTO save(CategoryDTO categoryDTO) {
		Category category = categoryRepository.save(categoryMapper.toEntity(categoryDTO));
		return categoryMapper.toDto(category);
	}

	@Override
	public void delete(String id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public CategoryDTO findByNameLike(String name) {
	    Category category = categoryRepository.findByNameLike(name);
		return categoryMapper.toDto(category);
	}

	@Override
	public List<CategoryDTO> findAll() {
	    List<Category> categories = categoryRepository.findAll();
		return categoryMapper.toDto(categories);
	}

	@Override
	public Optional<CategoryDTO> findOne(String id) {
		return categoryRepository.findById(id).map(categoryMapper::toDto);
	}

}
