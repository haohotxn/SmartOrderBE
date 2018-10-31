package org.itt.minhduc.service;

import org.itt.minhduc.domain.Order;
import org.itt.minhduc.domain.Product;
import org.itt.minhduc.domain.sub.ProductInOrder;
import org.itt.minhduc.repository.OrderRepository;
import org.itt.minhduc.repository.ProductRepository;
import org.itt.minhduc.service.dto.OrderDTO;
import org.itt.minhduc.service.dto.ProductDTO;
import org.itt.minhduc.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Service Implementation for managing Order.
 */
@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);
    
    private OrderRepository orderRepository;

    private OrderMapper orderMapper;
    @Autowired
    private ProductRepository productRepository;
    
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
        List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
        List<Order> orders= orderRepository.findAll();
        
        for(Order o:orders) {
        	OrderDTO orderDTO = new OrderDTO();
        	Iterator<ProductInOrder> iterator = o.getProducts().iterator();
        	List<Pair<Product, Integer>> productNQs = new ArrayList<Pair<Product,Integer>>();
        	while(iterator.hasNext()) {
        		ProductInOrder productInOrder = iterator.next();
        		String productId = productInOrder.getProductId();
        		Product product = productRepository.findById(productId).orElse(new Product());
        		Pair<Product, Integer> productNQ = Pair.of(product, productInOrder.getQuantity());
        		productNQs.add(productNQ);
        	}
        	orderDTO.setProducts(productNQs);
        	orderDTO.setStatus(o.getStatus());
        	orderDTO.setTableId(o.getTableId());
        	orderDTOs.add(orderDTO);
        }
        
        return new PageImpl<>(orderDTOs);
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
}
