package org.itt.minhduc.service.mapper;

import org.itt.minhduc.domain.*;
import org.itt.minhduc.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {



//    default Product fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        Product product = new Product();
//        product.setId(id);
//        return product;
//    }
}
