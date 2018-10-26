package org.itt.minhduc.service.mapper;

import org.itt.minhduc.domain.*;
import org.itt.minhduc.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Order and its DTO OrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {



    default Order fromId(String id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
