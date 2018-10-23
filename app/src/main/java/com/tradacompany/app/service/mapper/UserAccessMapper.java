package com.tradacompany.app.service.mapper;

import com.tradacompany.app.domain.*;
import com.tradacompany.app.service.dto.UserAccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAccess and its DTO UserAccessDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAccessMapper extends EntityMapper<UserAccessDTO, UserAccess> {



    default UserAccess fromId(String id) {
        if (id == null) {
            return null;
        }
        UserAccess userAccess = new UserAccess();
        userAccess.setId(id);
        return userAccess;
    }
}
