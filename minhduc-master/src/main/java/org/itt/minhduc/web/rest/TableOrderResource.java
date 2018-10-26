package org.itt.minhduc.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.itt.minhduc.service.TableOrderService;
import org.itt.minhduc.web.rest.errors.BadRequestAlertException;
import org.itt.minhduc.web.rest.util.HeaderUtil;
import org.itt.minhduc.web.rest.util.PaginationUtil;
import org.itt.minhduc.service.dto.TableOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TableOrder.
 */
@RestController
@RequestMapping("/api")
public class TableOrderResource {

    private final Logger log = LoggerFactory.getLogger(TableOrderResource.class);

    private static final String ENTITY_NAME = "tableOrder";

    private TableOrderService tableOrderService;

    public TableOrderResource(TableOrderService tableOrderService) {
        this.tableOrderService = tableOrderService;
    }

    /**
     * POST  /table-orders : Create a new tableOrder.
     *
     * @param tableOrderDTO the tableOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tableOrderDTO, or with status 400 (Bad Request) if the tableOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/table-orders")
    @Timed
    public ResponseEntity<TableOrderDTO> createTableOrder(@RequestBody TableOrderDTO tableOrderDTO) throws URISyntaxException {
        log.debug("REST request to save TableOrder : {}", tableOrderDTO);
        if (tableOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new tableOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TableOrderDTO result = tableOrderService.save(tableOrderDTO);
        return ResponseEntity.created(new URI("/api/table-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /table-orders : Updates an existing tableOrder.
     *
     * @param tableOrderDTO the tableOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tableOrderDTO,
     * or with status 400 (Bad Request) if the tableOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the tableOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/table-orders")
    @Timed
    public ResponseEntity<TableOrderDTO> updateTableOrder(@RequestBody TableOrderDTO tableOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TableOrder : {}", tableOrderDTO);
        if (tableOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TableOrderDTO result = tableOrderService.save(tableOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tableOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /table-orders : get all the tableOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tableOrders in body
     */
    @GetMapping("/table-orders")
    @Timed
    public ResponseEntity<List<TableOrderDTO>> getAllTableOrders(Pageable pageable) {
        log.debug("REST request to get a page of TableOrders");
        Page<TableOrderDTO> page = tableOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/table-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /table-orders/:id : get the "id" tableOrder.
     *
     * @param id the id of the tableOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tableOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/table-orders/{id}")
    @Timed
    public ResponseEntity<TableOrderDTO> getTableOrder(@PathVariable String id) {
        log.debug("REST request to get TableOrder : {}", id);
        Optional<TableOrderDTO> tableOrderDTO = tableOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tableOrderDTO);
    }

    /**
     * DELETE  /table-orders/:id : delete the "id" tableOrder.
     *
     * @param id the id of the tableOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/table-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTableOrder(@PathVariable String id) {
        log.debug("REST request to delete TableOrder : {}", id);
        tableOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
