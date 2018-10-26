package org.itt.minhduc.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.itt.minhduc.domain.CommonEntity;
import org.itt.minhduc.repository.CommonEntityRepository;
import org.itt.minhduc.web.rest.errors.BadRequestAlertException;
import org.itt.minhduc.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommonEntity.
 */
@RestController
@RequestMapping("/api")
public class CommonEntityResource {

    private final Logger log = LoggerFactory.getLogger(CommonEntityResource.class);

    private static final String ENTITY_NAME = "commonEntity";

    private CommonEntityRepository commonEntityRepository;

    public CommonEntityResource(CommonEntityRepository commonEntityRepository) {
        this.commonEntityRepository = commonEntityRepository;
    }

    /**
     * POST  /common-entities : Create a new commonEntity.
     *
     * @param commonEntity the commonEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commonEntity, or with status 400 (Bad Request) if the commonEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/common-entities")
    @Timed
    public ResponseEntity<CommonEntity> createCommonEntity(@RequestBody CommonEntity commonEntity) throws URISyntaxException {
        log.debug("REST request to save CommonEntity : {}", commonEntity);
        if (commonEntity.getId() != null) {
            throw new BadRequestAlertException("A new commonEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommonEntity result = commonEntityRepository.save(commonEntity);
        return ResponseEntity.created(new URI("/api/common-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /common-entities : Updates an existing commonEntity.
     *
     * @param commonEntity the commonEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commonEntity,
     * or with status 400 (Bad Request) if the commonEntity is not valid,
     * or with status 500 (Internal Server Error) if the commonEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/common-entities")
    @Timed
    public ResponseEntity<CommonEntity> updateCommonEntity(@RequestBody CommonEntity commonEntity) throws URISyntaxException {
        log.debug("REST request to update CommonEntity : {}", commonEntity);
        if (commonEntity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonEntity result = commonEntityRepository.save(commonEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commonEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /common-entities : get all the commonEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commonEntities in body
     */
    @GetMapping("/common-entities")
    @Timed
    public List<CommonEntity> getAllCommonEntities() {
        log.debug("REST request to get all CommonEntities");
        return commonEntityRepository.findAll();
    }

    /**
     * GET  /common-entities/:id : get the "id" commonEntity.
     *
     * @param id the id of the commonEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commonEntity, or with status 404 (Not Found)
     */
    @GetMapping("/common-entities/{id}")
    @Timed
    public ResponseEntity<CommonEntity> getCommonEntity(@PathVariable String id) {
        log.debug("REST request to get CommonEntity : {}", id);
        Optional<CommonEntity> commonEntity = commonEntityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commonEntity);
    }

    /**
     * DELETE  /common-entities/:id : delete the "id" commonEntity.
     *
     * @param id the id of the commonEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/common-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommonEntity(@PathVariable String id) {
        log.debug("REST request to delete CommonEntity : {}", id);

        commonEntityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
