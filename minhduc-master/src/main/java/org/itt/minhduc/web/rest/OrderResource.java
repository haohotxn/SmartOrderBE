package org.itt.minhduc.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.itt.minhduc.domain.enumeration.StatusOrder;
import org.itt.minhduc.service.OrderService;
import org.itt.minhduc.web.rest.errors.BadRequestAlertException;
import org.itt.minhduc.web.rest.util.HeaderUtil;
import org.itt.minhduc.web.rest.util.PaginationUtil;
import org.itt.minhduc.service.dto.OrderDTO;
import org.itt.minhduc.service.dto.OrderDTOWithTableName;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    private OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * POST  /orders : Create a new order.
     *
     * @param orderDTO the orderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderDTO, or with status 400 (Bad Request) if the order has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orders")
    @Timed
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDTO result = orderService.save(orderDTO);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orders : Updates an existing order.
     *
     * @param orderDTO the orderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderDTO,
     * or with status 400 (Bad Request) if the orderDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orders")
    @Timed
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to update Order : {}", orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderDTO result = orderService.save(orderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orders : get all the orders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orders in body
     */
    @GetMapping("/orders")
    @Timed
    public ResponseEntity<List<OrderDTOWithTableName>> getAllOrders(Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        Page<OrderDTOWithTableName> page = orderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orders/:id : get the "id" order.
     *
     * @param id the id of the orderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orders/{id}")
    @Timed
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        Optional<OrderDTO> orderDTO = orderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDTO);
    }

    /**
     * DELETE  /orders/:id : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
    
    /**
     * GET  /orders/{id} : get all the orders in a table.
     *
     * @param id of table
     * @return the ResponseEntity with status 200 (OK) and the list of orders REQUEST, PENDING, INPROGESS, COMPLETED in body
     */
    @GetMapping("/orders/findAll")
    @Timed
    public ResponseEntity<List<OrderDTOWithTableName>> getCurrentOrdersInTable(@RequestParam(value = "table_id", required = true) String tableid) {
    	log.debug("REST request to get a page of Orders");
		List<OrderDTOWithTableName> orderDtos = orderService.findCurrentOrdersInTable(tableid);
		return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
    
    /**
     * GET  /orders/{id} : get all the orders in a table.
     *
     * @param id of table
     * @return the ResponseEntity with status 200 (OK) and the list of orders REQUEST, PENDING, INPROGESS, COMPLETED in body
     */
    @GetMapping("/orders/findOrderBetween")
    @Timed
    public ResponseEntity<List<OrderDTOWithTableName>> getOrdersBetween(
    		@RequestParam(value = "from_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDate from,
    		@RequestParam(value = "to_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to
    		) {
    	log.debug("REST request to get a page of Orders");
    	
		List<OrderDTOWithTableName> orderDtos = orderService.findOrdersByDateBetween(
				from.atStartOfDay(ZoneId.systemDefault()).toInstant(),
				to.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
		return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
    
    /**
     * GET  /orders : get all the orders like status.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orders in body
     */
    @GetMapping("/orders/status")
    @Timed
    public ResponseEntity<List<OrderDTOWithTableName>> getAllOrderLikeStatus(@RequestParam(value = "status", required = false) StatusOrder status, Pageable pageable){
    	log.debug("REST request to get a page of Orders");
    	Page<OrderDTOWithTableName> page = orderService.findOrdersByStatus(status, pageable);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orders/status");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
