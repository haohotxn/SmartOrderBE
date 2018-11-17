package org.itt.minhduc.service.mapper;

import org.itt.minhduc.domain.*;
import org.itt.minhduc.service.dto.TableOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TableOrder and its DTO TableOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TableOrderMapper extends EntityMapper<TableOrderDTO, TableOrder> {

  
}
