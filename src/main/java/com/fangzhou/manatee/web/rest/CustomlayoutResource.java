package com.fangzhou.manatee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fangzhou.manatee.domain.Customlayout;

import com.fangzhou.manatee.repository.CustomlayoutRepository;
import com.fangzhou.manatee.web.rest.util.HeaderUtil;
// import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Customlayout.
 */
@RestController
@RequestMapping("/api")
public class CustomlayoutResource {

    private final Logger log = LoggerFactory.getLogger(CustomlayoutResource.class);

    private static final String ENTITY_NAME = "customlayout";

    private final CustomlayoutRepository customlayoutRepository;

    public CustomlayoutResource(CustomlayoutRepository customlayoutRepository) {
        this.customlayoutRepository = customlayoutRepository;
    }

    /**
     * POST  /customlayouts : Create a new customlayout.
     *
     * @param customlayout the customlayout to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customlayout, or with status 400 (Bad Request) if the customlayout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customlayouts")
    @Timed
    public ResponseEntity<Customlayout> createCustomlayout(@RequestBody Customlayout customlayout) throws URISyntaxException {
        log.debug("REST request to save Customlayout : {}", customlayout);
        if (customlayout.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customlayout cannot already have an ID")).body(null);
        }
        Customlayout result = customlayoutRepository.save(customlayout);
        return ResponseEntity.created(new URI("/api/customlayouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customlayouts : Updates an existing customlayout.
     *
     * @param customlayout the customlayout to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customlayout,
     * or with status 400 (Bad Request) if the customlayout is not valid,
     * or with status 500 (Internal Server Error) if the customlayout couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customlayouts")
    @Timed
    public ResponseEntity<Customlayout> updateCustomlayout(@RequestBody Customlayout customlayout) throws URISyntaxException {
        log.debug("REST request to update Customlayout : {}", customlayout);
        if (customlayout.getId() == null) {
            return createCustomlayout(customlayout);
        }
        Customlayout result = customlayoutRepository.save(customlayout);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customlayout.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customlayouts : get all the customlayouts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customlayouts in body
     */
    @GetMapping("/customlayouts")
    @Timed
    public List<Customlayout> getAllCustomlayouts() {
        log.debug("REST request to get all Customlayouts");
        return customlayoutRepository.findAll();
    }

    /**
     * GET  /customlayouts/:id : get the "id" customlayout.
     *
     * @param id the id of the customlayout to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customlayout, or with status 404 (Not Found)
     */
    @GetMapping("/customlayouts/{id}")
    @Timed
    public ResponseEntity<Customlayout> getCustomlayout(@PathVariable Long id) {
        log.debug("REST request to get Customlayout : {}", id);
        Customlayout customlayout = customlayoutRepository.findOne(id);
        return Optional.ofNullable(customlayout)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customlayout));
    }

    /**
     * DELETE  /customlayouts/:id : delete the "id" customlayout.
     *
     * @param id the id of the customlayout to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customlayouts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomlayout(@PathVariable Long id) {
        log.debug("REST request to delete Customlayout : {}", id);
        customlayoutRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
