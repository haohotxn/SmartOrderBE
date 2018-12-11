package org.itt.minhduc.service;

import org.itt.minhduc.domain.Order;
import org.itt.minhduc.domain.enumeration.StatusOrder;
import org.itt.minhduc.repository.OrderRepository;
import org.itt.minhduc.service.dto.OrderDTO;
import org.itt.minhduc.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Order.
 */
@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);

        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable)
            .map(orderMapper::toDto);
    }


    /**
     * Get one order by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<OrderDTO> findOne(String id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id)
            .map(orderMapper::toDto);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
    
    /**
     * Get all the current orders in table.
     *
     * @param tableId
     * @return the list of entities
     */
    public List<OrderDTO> findCurrentOrdersInTable(String tableId) {
        log.debug("Request to get all Orders");
        List<Order> orders = orderRepository.findByTableIdLike(tableId);
		List<Order> currentOrdersInTable = new ArrayList<Order>();
		for (Order order : orders) {
			if ((order.getStatus() == StatusOrder.COMPLETED) || (order.getStatus() == StatusOrder.REQUEST)) {
				currentOrdersInTable.add(order);
			}
		}
        return orderMapper.toDto(currentOrdersInTable);
    }
    
    /**
     * Get all orders between.
     *
     * @param from, to
     * @return the list of entities
     */
    public List<OrderDTO> findOrdersByDateBetween(Instant from, Instant to) {
        log.debug("Request to get all Orders");
        List<Order> orders = orderRepository.findAllByCreatedDateBetween(from, to);
        return orderMapper.toDto(orders);
    }
    
    /**
     * Get all orders with status.
     *
     * @param status
     * @return the list of entities
     */
    public Page<OrderDTO> findOrdersByStatus(StatusOrder status, Pageable pageable){
    	log.debug("Request to get all Orders by Status");
    	return orderRepository.findAllByStatusLike(status, pageable).map(orderMapper::toDto);
    }
}
