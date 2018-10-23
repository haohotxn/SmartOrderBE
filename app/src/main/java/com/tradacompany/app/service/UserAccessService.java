package com.tradacompany.app.service;

import com.tradacompany.app.domain.UserAccess;
import com.tradacompany.app.repository.UserAccessRepository;
import com.tradacompany.app.service.dto.UserAccessDTO;
import com.tradacompany.app.service.mapper.UserAccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing UserAccess.
 */
@Service
public class UserAccessService {

    private final Logger log = LoggerFactory.getLogger(UserAccessService.class);

    private final UserAccessRepository userAccessRepository;

    private final UserAccessMapper userAccessMapper;

    public UserAccessService(UserAccessRepository userAccessRepository, UserAccessMapper userAccessMapper) {
        this.userAccessRepository = userAccessRepository;
        this.userAccessMapper = userAccessMapper;
    }

    /**
     * Save a userAccess.
     *
     * @param userAccessDTO the entity to save
     * @return the persisted entity
     */
    public UserAccessDTO save(UserAccessDTO userAccessDTO) {
        log.debug("Request to save UserAccess : {}", userAccessDTO);

        UserAccess userAccess = userAccessMapper.toEntity(userAccessDTO);
        userAccess = userAccessRepository.save(userAccess);
        return userAccessMapper.toDto(userAccess);
    }

    /**
     * Get all the userAccesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<UserAccessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAccesses");
        return userAccessRepository.findAll(pageable)
            .map(userAccessMapper::toDto);
    }


    /**
     * Get one userAccess by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<UserAccessDTO> findOne(String id) {
        log.debug("Request to get UserAccess : {}", id);
        return userAccessRepository.findById(id)
            .map(userAccessMapper::toDto);
    }

    /**
     * Delete the userAccess by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete UserAccess : {}", id);
        userAccessRepository.deleteById(id);
    }
}
