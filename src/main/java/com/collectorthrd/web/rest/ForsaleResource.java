package com.collectorthrd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.collectorthrd.domain.Forsale;
import com.collectorthrd.service.ForsaleService;
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
 * REST controller for managing Forsale.
 */
@RestController
@RequestMapping("/api")
public class ForsaleResource {

    private final Logger log = LoggerFactory.getLogger(ForsaleResource.class);
        
    @Inject
    private ForsaleService forsaleService;
    
    /**
     * POST  /forsales -> Create a new forsale.
     */
    @RequestMapping(value = "/forsales",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Forsale> createForsale(@Valid @RequestBody Forsale forsale) throws URISyntaxException {
        log.debug("REST request to save Forsale : {}", forsale);
        if (forsale.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("forsale", "idexists", "A new forsale cannot already have an ID")).body(null);
        }
        Forsale result = forsaleService.save(forsale);
        return ResponseEntity.created(new URI("/api/forsales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("forsale", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forsales -> Updates an existing forsale.
     */
    @RequestMapping(value = "/forsales",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Forsale> updateForsale(@Valid @RequestBody Forsale forsale) throws URISyntaxException {
        log.debug("REST request to update Forsale : {}", forsale);
        if (forsale.getId() == null) {
            return createForsale(forsale);
        }
        Forsale result = forsaleService.save(forsale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("forsale", forsale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forsales -> get all the forsales.
     */
    @RequestMapping(value = "/forsales",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Forsale> getAllForsales() {
        log.debug("REST request to get all Forsales");
        return forsaleService.findAll();
            }

    /**
     * GET  /forsales/:id -> get the "id" forsale.
     */
    @RequestMapping(value = "/forsales/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Forsale> getForsale(@PathVariable Long id) {
        log.debug("REST request to get Forsale : {}", id);
        Forsale forsale = forsaleService.findOne(id);
        return Optional.ofNullable(forsale)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /forsales/:id -> delete the "id" forsale.
     */
    @RequestMapping(value = "/forsales/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteForsale(@PathVariable Long id) {
        log.debug("REST request to delete Forsale : {}", id);
        forsaleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("forsale", id.toString())).build();
    }

    /**
     * SEARCH  /_search/forsales/:query -> search for the forsale corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/forsales/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Forsale> searchForsales(@PathVariable String query) {
        log.debug("Request to search Forsales for query {}", query);
        return forsaleService.search(query);
    }
}
