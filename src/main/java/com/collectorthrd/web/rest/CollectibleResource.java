package com.collectorthrd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.collectorthrd.domain.Collectible;
import com.collectorthrd.service.CollectibleService;
import com.collectorthrd.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Collectible.
 */
@RestController
@RequestMapping("/api")
public class CollectibleResource {

    private final Logger log = LoggerFactory.getLogger(CollectibleResource.class);
        
    @Inject
    private CollectibleService collectibleService;
    
    /**
     * POST  /collectibles -> Create a new collectible.
     */
    @RequestMapping(value = "/collectibles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Collectible> createCollectible(@Valid @RequestBody Collectible collectible) throws URISyntaxException {
        log.debug("REST request to save Collectible : {}", collectible);
        if (collectible.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("collectible", "idexists", "A new collectible cannot already have an ID")).body(null);
        }
        Collectible result = collectibleService.save(collectible);
        return ResponseEntity.created(new URI("/api/collectibles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("collectible", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collectibles -> Updates an existing collectible.
     */
    @RequestMapping(value = "/collectibles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Collectible> updateCollectible(@Valid @RequestBody Collectible collectible) throws URISyntaxException {
        log.debug("REST request to update Collectible : {}", collectible);
        if (collectible.getId() == null) {
            return createCollectible(collectible);
        }
        Collectible result = collectibleService.save(collectible);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("collectible", collectible.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collectibles -> get all the collectibles.
     */
    @RequestMapping(value = "/collectibles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Collectible> getAllCollectibles() {
        log.debug("REST request to get all Collectibles");
        return collectibleService.findAll();
            }

    /**
     * GET  /collectibles/:id -> get the "id" collectible.
     */
    @RequestMapping(value = "/collectibles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Collectible> getCollectible(@PathVariable Long id) {
        log.debug("REST request to get Collectible : {}", id);
        Collectible collectible = collectibleService.findOne(id);
        return Optional.ofNullable(collectible)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /collectibles/:id -> delete the "id" collectible.
     */
    @RequestMapping(value = "/collectibles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCollectible(@PathVariable Long id) {
        log.debug("REST request to delete Collectible : {}", id);
        collectibleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("collectible", id.toString())).build();
    }

    /**
     * SEARCH  /_search/collectibles/:query -> search for the collectible corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/collectibles/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Collectible> searchCollectibles(@PathVariable String query) {
        log.debug("Request to search Collectibles for query {}", query);
        return collectibleService.search(query);
    }
}
