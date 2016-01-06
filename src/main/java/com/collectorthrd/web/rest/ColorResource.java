package com.collectorthrd.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.collectorthrd.domain.Color;
import com.collectorthrd.service.ColorService;
import com.collectorthrd.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Color.
 */
@RestController
@RequestMapping("/api")
public class ColorResource {

    private final Logger log = LoggerFactory.getLogger(ColorResource.class);
        
    @Inject
    private ColorService colorService;
    
    /**
     * POST  /colors -> Create a new color.
     */
    @RequestMapping(value = "/colors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Color> createColor(@Valid @RequestBody Color color) throws URISyntaxException {
        log.debug("REST request to save Color : {}", color);
        if (color.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("color", "idexists", "A new color cannot already have an ID")).body(null);
        }
        Color result = colorService.save(color);
        return ResponseEntity.created(new URI("/api/colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("color", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /colors -> Updates an existing color.
     */
    @RequestMapping(value = "/colors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Color> updateColor(@Valid @RequestBody Color color) throws URISyntaxException {
        log.debug("REST request to update Color : {}", color);
        if (color.getId() == null) {
            return createColor(color);
        }
        Color result = colorService.save(color);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("color", color.getId().toString()))
            .body(result);
    }

    /**
     * GET  /colors -> get all the colors.
     */
    @RequestMapping(value = "/colors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Color> getAllColors() {
        log.debug("REST request to get all Colors");
        return colorService.findAll();
            }

    /**
     * GET  /colors/:id -> get the "id" color.
     */
    @RequestMapping(value = "/colors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Color> getColor(@PathVariable Long id) {
        log.debug("REST request to get Color : {}", id);
        Color color = colorService.findOne(id);
        return Optional.ofNullable(color)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /colors/:id -> delete the "id" color.
     */
    @RequestMapping(value = "/colors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        log.debug("REST request to delete Color : {}", id);
        colorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("color", id.toString())).build();
    }

    /**
     * SEARCH  /_search/colors/:query -> search for the color corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/colors/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Color> searchColors(@PathVariable String query) {
        log.debug("Request to search Colors for query {}", query);
        return colorService.search(query);
    }
}
