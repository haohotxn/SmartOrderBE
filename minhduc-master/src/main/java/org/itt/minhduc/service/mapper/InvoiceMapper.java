package org.itt.minhduc.service.mapper;

import org.itt.minhduc.domain.*;
import org.itt.minhduc.service.dto.InvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invoice and its DTO InvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {
   
}
