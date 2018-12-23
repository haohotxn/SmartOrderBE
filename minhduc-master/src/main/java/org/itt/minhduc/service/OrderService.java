package org.itt.minhduc.service;

import org.itt.minhduc.domain.Order;
import org.itt.minhduc.domain.enumeration.StatusOrder;
import org.itt.minhduc.repository.OrderRepository;
import org.itt.minhduc.repository.TableOrderRepository;
import org.itt.minhduc.service.dto.OrderDTO;
import org.itt.minhduc.service.dto.OrderDTOWithTableName;
import org.itt.minhduc.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    
    @Autowired
    private TableOrderRepository tableOrderRepository;

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

//    /**
//     * Get all the orders.
//     *
//     * @param pageable the pagination information
//     * @return the list of entities
//     */
//    public Page<OrderDTO> findAll(Pageable pageable) {
//        log.debug("Request to get all Orders");
//        return orderRepository.findAll(pageable)
//            .map(orderMapper::toDto);
//    }
    
    /**
     * Get all the orders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<OrderDTOWithTableName> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        List<OrderDTO> orderDTOs = orderRepository.findAll(pageable).map(orderMapper::toDto).getContent();
        List<OrderDTOWithTableName> orderDtoWithTableNames = getOrderDtoWithTableName(orderDTOs);
        return new PageImpl(orderDtoWithTableNames, pageable, orderDtoWithTableNames.size());
    }

    private List<OrderDTOWithTableName> getOrderDtoWithTableName(List<OrderDTO> orderDTOs){
    	List<OrderDTOWithTableName> orderDtoWithTableNames = new ArrayList<OrderDTOWithTableName>();
//      log.debug("List<OrderDTO> orderDTOs " + orderDTOs.size());
//      
//      for(OrderDTO order : orderDTOs) {
//      	log.debug("hao.ho start loop 1111111 "+order.getId());
//      	log.debug("tableOrderRepository + " + tableOrderRepository.findById(order.getTableId()).toString());
//      	log.debug("getTableId + " + order.getId());
//      	
//      }
      
      for(OrderDTO order : orderDTOs) {
//      	log.debug("hao.ho start loop ");
//      	log.debug("tableOrderRepository + " + tableOrderRepository.findById(order.getTableId()).toString());
//      	log.debug("getTableId + " + order.getId());
//      	log.debug("orderRepository + " + orderRepository.findById(order.getId()).toString());
      	String tableName = tableOrderRepository.findById(order.getTableId()).get().getName();
//      	log.debug("tempOrder.setTableName 1 + " + tableName);
      	OrderDTOWithTableName tempOrder = new OrderDTOWithTableName();
      	tempOrder.setId(order.getId());
      	tempOrder.setCreatedBy(order.getCreatedBy());
      	tempOrder.setCreatedDate(order.getCreatedDate());
      	tempOrder.setLastModifiedBy(order.getLastModifiedBy());
      	tempOrder.setLastModifiedDate(order.getLastModifiedDate());
      	tempOrder.setProductsInOrder(order.getProductsInOrder());
      	tempOrder.setStatus(order.getStatus());
      	tempOrder.setTableName(tableName);
//      	log.debug("tempOrder.setTableName 2 + " + tableName);
      	
      	orderDtoWithTableNames.add(tempOrder);
      }
      
      return orderDtoWithTableNames;
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
    public List<OrderDTOWithTableName> findCurrentOrdersInTable(String tableId) {
        log.debug("Request to get all Orders");
        List<Order> orders = orderRepository.findByTableIdLike(tableId);
		List<Order> currentOrdersInTable = new ArrayList<Order>();
		for (Order order : orders) {
			if ((order.getStatus() == StatusOrder.COMPLETED) || (order.getStatus() == StatusOrder.REQUEST)) {
				currentOrdersInTable.add(order);
			}
		}
		return getOrderDtoWithTableName(orderMapper.toDto(currentOrdersInTable));
//        return orderMapper.toDto(currentOrdersInTable);
    }
    
    /**
     * Get all orders between.
     *
     * @param from, to
     * @return the list of entities
     */
    public List<OrderDTOWithTableName> findOrdersByDateBetween(Instant from, Instant to) {
        log.debug("Request to get all Orders");
        List<Order> orders = orderRepository.findAllByCreatedDateBetween(from, to);
        return getOrderDtoWithTableName(orderMapper.toDto(orders));
//        return orderMapper.toDto(orders);
    }
    
    /**
     * Get all orders with status.
     *
     * @param status
     * @return the list of entities
     */
    public Page<OrderDTOWithTableName> findOrdersByStatus(StatusOrder status, Pageable pageable){
    	log.debug("Request to get all Orders by Status");
    	List<Order> orders = orderRepository.findAllByStatusLike(status);
    	List<OrderDTOWithTableName> orderDtoWithTableNames = getOrderDtoWithTableName(orderMapper.toDto(orders));
        return new PageImpl(orderDtoWithTableNames, pageable, orderDtoWithTableNames.size());
    	
    }
}
