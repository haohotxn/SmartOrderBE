package org.itt.minhduc.service;

import org.itt.minhduc.domain.Invoice;
import org.itt.minhduc.repository.InvoiceRepository;
import org.itt.minhduc.service.dto.InvoiceDTO;
import org.itt.minhduc.service.mapper.InvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Invoice.
 */
@Service
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private InvoiceRepository invoiceRepository;

    private InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * Save a invoice.
     *
     * @param invoiceDTO the entity to save
     * @return the persisted entity
     */
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        log.debug("Request to save Invoice : {}", invoiceDTO);

        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<InvoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable)
            .map(invoiceMapper::toDto);
    }


    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<InvoiceDTO> findOne(String id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id)
            .map(invoiceMapper::toDto);
    }

    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
