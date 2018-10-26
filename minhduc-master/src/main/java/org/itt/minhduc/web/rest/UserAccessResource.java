package org.itt.minhduc.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.itt.minhduc.service.UserAccessService;
import org.itt.minhduc.web.rest.errors.BadRequestAlertException;
import org.itt.minhduc.web.rest.util.HeaderUtil;
import org.itt.minhduc.web.rest.util.PaginationUtil;
import org.itt.minhduc.service.dto.UserAccessDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserAccess.
 */
@RestController
@RequestMapping("/api")
public class UserAccessResource {

    private final Logger log = LoggerFactory.getLogger(UserAccessResource.class);

    private static final String ENTITY_NAME = "userAccess";

    private UserAccessService userAccessService;

    public UserAccessResource(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    /**
     * POST  /user-accesses : Create a new userAccess.
     *
     * @param userAccessDTO the userAccessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAccessDTO, or with status 400 (Bad Request) if the userAccess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-accesses")
    @Timed
    public ResponseEntity<UserAccessDTO> createUserAccess(@RequestBody UserAccessDTO userAccessDTO) throws URISyntaxException {
        log.debug("REST request to save UserAccess : {}", userAccessDTO);
        if (userAccessDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAccessDTO result = userAccessService.save(userAccessDTO);
        return ResponseEntity.created(new URI("/api/user-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-accesses : Updates an existing userAccess.
     *
     * @param userAccessDTO the userAccessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAccessDTO,
     * or with status 400 (Bad Request) if the userAccessDTO is not valid,
     * or with status 500 (Internal Server Error) if the userAccessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-accesses")
    @Timed
    public ResponseEntity<UserAccessDTO> updateUserAccess(@RequestBody UserAccessDTO userAccessDTO) throws URISyntaxException {
        log.debug("REST request to update UserAccess : {}", userAccessDTO);
        if (userAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAccessDTO result = userAccessService.save(userAccessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAccessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-accesses : get all the userAccesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userAccesses in body
     */
    @GetMapping("/user-accesses")
    @Timed
    public ResponseEntity<List<UserAccessDTO>> getAllUserAccesses(Pageable pageable) {
        log.debug("REST request to get a page of UserAccesses");
        Page<UserAccessDTO> page = userAccessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-accesses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-accesses/:id : get the "id" userAccess.
     *
     * @param id the id of the userAccessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAccessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-accesses/{id}")
    @Timed
    public ResponseEntity<UserAccessDTO> getUserAccess(@PathVariable String id) {
        log.debug("REST request to get UserAccess : {}", id);
        Optional<UserAccessDTO> userAccessDTO = userAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAccessDTO);
    }

    /**
     * DELETE  /user-accesses/:id : delete the "id" userAccess.
     *
     * @param id the id of the userAccessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserAccess(@PathVariable String id) {
        log.debug("REST request to delete UserAccess : {}", id);
        userAccessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
