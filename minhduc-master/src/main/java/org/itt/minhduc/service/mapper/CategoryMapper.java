package org.itt.minhduc.service.mapper;

import org.itt.minhduc.domain.Category;
import org.itt.minhduc.service.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

	default Category fromId(String id) {
		if (id == null) {
			return null;
		}
		Category category = new Category();
		category.setId(id);
		return category;
	}
}
