package com.collectorthrd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.collectorthrd.domain.Keyword;
import com.collectorthrd.service.KeywordService;
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
 * REST controller for managing Keyword.
 */
@RestController
@RequestMapping("/api")
public class KeywordResource {

    private final Logger log = LoggerFactory.getLogger(KeywordResource.class);
        
    @Inject
    private KeywordService keywordService;
    
    /**
     * POST  /keywords -> Create a new keyword.
     */
    @RequestMapping(value = "/keywords",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Keyword> createKeyword(@Valid @RequestBody Keyword keyword) throws URISyntaxException {
        log.debug("REST request to save Keyword : {}", keyword);
        if (keyword.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("keyword", "idexists", "A new keyword cannot already have an ID")).body(null);
        }
        Keyword result = keywordService.save(keyword);
        return ResponseEntity.created(new URI("/api/keywords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("keyword", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /keywords -> Updates an existing keyword.
     */
    @RequestMapping(value = "/keywords",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Keyword> updateKeyword(@Valid @RequestBody Keyword keyword) throws URISyntaxException {
        log.debug("REST request to update Keyword : {}", keyword);
        if (keyword.getId() == null) {
            return createKeyword(keyword);
        }
        Keyword result = keywordService.save(keyword);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("keyword", keyword.getId().toString()))
            .body(result);
    }

    /**
     * GET  /keywords -> get all the keywords.
     */
    @RequestMapping(value = "/keywords",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Keyword> getAllKeywords() {
        log.debug("REST request to get all Keywords");
        return keywordService.findAll();
            }

    /**
     * GET  /keywords/:id -> get the "id" keyword.
     */
    @RequestMapping(value = "/keywords/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Keyword> getKeyword(@PathVariable Long id) {
        log.debug("REST request to get Keyword : {}", id);
        Keyword keyword = keywordService.findOne(id);
        return Optional.ofNullable(keyword)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /keywords/:id -> delete the "id" keyword.
     */
    @RequestMapping(value = "/keywords/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long id) {
        log.debug("REST request to delete Keyword : {}", id);
        keywordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("keyword", id.toString())).build();
    }

    /**
     * SEARCH  /_search/keywords/:query -> search for the keyword corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/keywords/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Keyword> searchKeywords(@PathVariable String query) {
        log.debug("Request to search Keywords for query {}", query);
        return keywordService.search(query);
    }
}
