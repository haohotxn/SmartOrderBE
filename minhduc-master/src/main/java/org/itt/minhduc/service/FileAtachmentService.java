package org.itt.minhduc.service;

import java.util.List;
import java.util.Optional;

import org.itt.minhduc.service.dto.FileAtachmentDTO;

public interface FileAtachmentService {
	
	FileAtachmentDTO save(FileAtachmentDTO fileAtachmentDTO);

	void delete(String id);

	Optional<FileAtachmentDTO> findOne(String id);

	List<FileAtachmentDTO> findAll();
}
