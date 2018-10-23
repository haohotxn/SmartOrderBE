package com.tradacompany.app.service.mapper;

import com.tradacompany.app.domain.*;
import com.tradacompany.app.service.dto.TableOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TableOrder and its DTO TableOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TableOrderMapper extends EntityMapper<TableOrderDTO, TableOrder> {



    default TableOrder fromId(String id) {
        if (id == null) {
            return null;
        }
        TableOrder tableOrder = new TableOrder();
        tableOrder.setId(id);
        return tableOrder;
    }
}
