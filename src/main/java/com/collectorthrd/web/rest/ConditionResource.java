package com.collectorthrd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.collectorthrd.domain.Condition;
import com.collectorthrd.service.ConditionService;
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
 * REST controller for managing Condition.
 */
@RestController
@RequestMapping("/api")
public class ConditionResource {

    private final Logger log = LoggerFactory.getLogger(ConditionResource.class);
        
    @Inject
    private ConditionService conditionService;
    
    /**
     * POST  /conditions -> Create a new condition.
     */
    @RequestMapping(value = "/conditions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Condition> createCondition(@Valid @RequestBody Condition condition) throws URISyntaxException {
        log.debug("REST request to save Condition : {}", condition);
        if (condition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("condition", "idexists", "A new condition cannot already have an ID")).body(null);
        }
        Condition result = conditionService.save(condition);
        return ResponseEntity.created(new URI("/api/conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("condition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conditions -> Updates an existing condition.
     */
    @RequestMapping(value = "/conditions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Condition> updateCondition(@Valid @RequestBody Condition condition) throws URISyntaxException {
        log.debug("REST request to update Condition : {}", condition);
        if (condition.getId() == null) {
            return createCondition(condition);
        }
        Condition result = conditionService.save(condition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("condition", condition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conditions -> get all the conditions.
     */
    @RequestMapping(value = "/conditions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Condition> getAllConditions() {
        log.debug("REST request to get all Conditions");
        return conditionService.findAll();
            }

    /**
     * GET  /conditions/:id -> get the "id" condition.
     */
    @RequestMapping(value = "/conditions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Condition> getCondition(@PathVariable Long id) {
        log.debug("REST request to get Condition : {}", id);
        Condition condition = conditionService.findOne(id);
        return Optional.ofNullable(condition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /conditions/:id -> delete the "id" condition.
     */
    @RequestMapping(value = "/conditions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        log.debug("REST request to delete Condition : {}", id);
        conditionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("condition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/conditions/:query -> search for the condition corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/conditions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Condition> searchConditions(@PathVariable String query) {
        log.debug("Request to search Conditions for query {}", query);
        return conditionService.search(query);
    }
}
