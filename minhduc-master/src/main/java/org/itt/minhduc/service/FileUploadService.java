package org.itt.minhduc.service;

import java.util.Optional;

import org.itt.minhduc.service.dto.UploadFileResponse;

public interface FileUploadService {
	
	void save(UploadFileResponse uploadFileResponse);

	void delete(String id);

	Optional<UploadFileResponse> findOne(String id);
}
