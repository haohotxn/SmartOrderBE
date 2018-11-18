package org.itt.minhduc.service.mapper;

import org.itt.minhduc.domain.FileAtachment;
import org.itt.minhduc.service.dto.FileAtachmentDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring" , uses = {})
public interface FileAtachmentMapper extends EntityMapper<FileAtachmentDTO, FileAtachment>{

}
