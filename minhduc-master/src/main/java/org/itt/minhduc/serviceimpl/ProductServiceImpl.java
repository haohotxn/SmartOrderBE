package org.itt.minhduc.serviceimpl;



import org.bson.types.ObjectId;
import org.itt.minhduc.repository.ProductRepository;
import org.itt.minhduc.service.ProductServiceCustom;
import org.itt.minhduc.service.dto.ProductDTO;
import org.itt.minhduc.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductServiceCustom {
	@Autowired
	ProductMapper productMapper;

	@Autowired
	ProductRepository productRepository;
	

	@Override
	public Page<ProductDTO> getPage(String id, Pageable pageable) {
		return productRepository.findAll(new ObjectId(id), pageable)
				.map(productMapper::toDto);
	}

}
