package org.itt.minhduc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.itt.minhduc.domain.FileAtachment;
import org.itt.minhduc.repository.FileAtachmentRepository;
import org.itt.minhduc.service.FileAtachmentService;
import org.itt.minhduc.service.dto.FileAtachmentDTO;
import org.itt.minhduc.service.mapper.FileAtachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileAtachmentServiceImpl implements FileAtachmentService {
	@Autowired
	private FileAtachmentRepository fileAtachmentRepository;

	@Autowired
	private FileAtachmentMapper fileAtachmentMapper;

	@Override
	public FileAtachmentDTO save(FileAtachmentDTO fileAtachmentDTO) {
		FileAtachment fileAtachment = fileAtachmentRepository.save(fileAtachmentMapper.toEntity(fileAtachmentDTO));
		return fileAtachmentMapper.toDto(fileAtachment);
	}

	@Override
	public void delete(String id) {
		fileAtachmentRepository.deleteById(id);
	}

	@Override
	public Optional<FileAtachmentDTO> findOne(String id) {
		return fileAtachmentRepository.findById(id).map(fileAtachmentMapper::toDto);
	}

	@Override
	public List<FileAtachmentDTO> findAll() {
		List<FileAtachment> fileAtachments = fileAtachmentRepository.findAll();
		return fileAtachmentMapper.toDto(fileAtachments);
	}

}
