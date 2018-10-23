package com.tradacompany.app.service;

import com.tradacompany.app.domain.TableOrder;
import com.tradacompany.app.repository.TableOrderRepository;
import com.tradacompany.app.service.dto.TableOrderDTO;
import com.tradacompany.app.service.mapper.TableOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing TableOrder.
 */
@Service
public class TableOrderService {

    private final Logger log = LoggerFactory.getLogger(TableOrderService.class);

    private final TableOrderRepository tableOrderRepository;

    private final TableOrderMapper tableOrderMapper;

    public TableOrderService(TableOrderRepository tableOrderRepository, TableOrderMapper tableOrderMapper) {
        this.tableOrderRepository = tableOrderRepository;
        this.tableOrderMapper = tableOrderMapper;
    }

    /**
     * Save a tableOrder.
     *
     * @param tableOrderDTO the entity to save
     * @return the persisted entity
     */
    public TableOrderDTO save(TableOrderDTO tableOrderDTO) {
        log.debug("Request to save TableOrder : {}", tableOrderDTO);

        TableOrder tableOrder = tableOrderMapper.toEntity(tableOrderDTO);
        tableOrder = tableOrderRepository.save(tableOrder);
        return tableOrderMapper.toDto(tableOrder);
    }

    /**
     * Get all the tableOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<TableOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TableOrders");
        return tableOrderRepository.findAll(pageable)
            .map(tableOrderMapper::toDto);
    }


    /**
     * Get one tableOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<TableOrderDTO> findOne(String id) {
        log.debug("Request to get TableOrder : {}", id);
        return tableOrderRepository.findById(id)
            .map(tableOrderMapper::toDto);
    }

    /**
     * Delete the tableOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete TableOrder : {}", id);
        tableOrderRepository.deleteById(id);
    }
}
