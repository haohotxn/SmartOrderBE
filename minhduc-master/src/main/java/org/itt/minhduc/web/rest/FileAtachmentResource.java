package org.itt.minhduc.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.itt.minhduc.service.FileAtachmentService;
import org.itt.minhduc.service.dto.FileAtachmentDTO;
import org.itt.minhduc.web.rest.errors.BadRequestAlertException;
import org.itt.minhduc.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class FileAtachmentResource {
	private final Logger log = LoggerFactory.getLogger(FileAtachmentResource.class);

	private static final String ENTITY_NAME = "file_atachment";

	private FileAtachmentService fileAtachmentService;

	public FileAtachmentResource(FileAtachmentService fileAtachmentService) {
		this.fileAtachmentService = fileAtachmentService;
	}

	@PostMapping("/file_atachment")
	@Timed
	public ResponseEntity<FileAtachmentDTO> createFileAtachment(@RequestBody FileAtachmentDTO fileAtachmentDTO)
			throws URISyntaxException {
		if (fileAtachmentDTO.getId() != null) {
			throw new BadRequestAlertException("a new file cannot already have an id ", ENTITY_NAME, "idexists");
		}
		log.debug("REST request to save file : {}", fileAtachmentDTO);
		FileAtachmentDTO result = fileAtachmentService.save(fileAtachmentDTO);

		return ResponseEntity.created(new URI("api/file_atachment" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}
	
	@PutMapping("/file_atachment")
	@Timed
	public ResponseEntity<FileAtachmentDTO> updateFileAtachment(@RequestBody FileAtachmentDTO fileAtachmentDTO) throws URISyntaxException {
		log.debug("REST request to update file : {}", fileAtachmentDTO);
		if (fileAtachmentDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		FileAtachmentDTO result = fileAtachmentService.save(fileAtachmentDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileAtachmentDTO.getId().toString())).body(result);
	}
	
	@GetMapping("/file_atachment/{id}")
	@Timed
	public ResponseEntity<FileAtachmentDTO> getFileAtachment(@PathVariable String id) throws URISyntaxException {
		log.debug("REST request to get file: {}", id);
		Optional<FileAtachmentDTO> fileAtachmentDTO = fileAtachmentService.findOne(id);
		return ResponseUtil.wrapOrNotFound(fileAtachmentDTO);
	}
	
	@GetMapping("/file_atachment")
	@Timed
	public ResponseEntity<List<FileAtachmentDTO>> getAllFileAtachment() throws URISyntaxException {
		log.debug("REST request to get all File: {}");
		List<FileAtachmentDTO> fileAtachmentDTOs = fileAtachmentService.findAll();
		return ResponseEntity.ok().body(fileAtachmentDTOs);
	}
	
	@DeleteMapping("/file_atachment/{id}")
	@Timed
	public ResponseEntity<Void> deleteFileAtachment(@PathVariable String id) throws URISyntaxException {
		log.debug("REST request to delete file : {}", id);
		fileAtachmentService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, id)).build();
	}
}
