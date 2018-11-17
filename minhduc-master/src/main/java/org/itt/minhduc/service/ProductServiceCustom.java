package org.itt.minhduc.service;

import org.itt.minhduc.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceCustom {
	   Page<ProductDTO> getPage(String id,Pageable pageable);
}
