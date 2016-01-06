package com.collectorthrd.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.collectorthrd.domain.Color;
import com.collectorthrd.repository.ColorRepository;
import com.collectorthrd.repository.search.ColorSearchRepository;
import com.collectorthrd.service.ColorService;

/**
 * Service Implementation for managing Color.
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorService{

    private final Logger log = LoggerFactory.getLogger(ColorServiceImpl.class);
    
    @Inject
    private ColorRepository colorRepository;
    
    @Inject
    private ColorSearchRepository colorSearchRepository;
    
    /**
     * Save a color.
     * @return the persisted entity
     */
    public Color save(Color color) {
        log.debug("Request to save Color : {}", color);
        Color result = colorRepository.save(color);
        colorSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the colors.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Color> findAll() {
        log.debug("Request to get all Colors");
        List<Color> result = colorRepository.findAll();
        return result;
    }

    /**
     *  get one color by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Color findOne(Long id) {
        log.debug("Request to get Color : {}", id);
        Color color = colorRepository.findOne(id);
        return color;
    }

    /**
     *  delete the  color by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Color : {}", id);
        colorRepository.delete(id);
        colorSearchRepository.delete(id);
    }

    /**
     * search for the color corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Color> search(String query) {
        
        log.debug("REST request to search Colors for query {}", query);
        return StreamSupport
            .stream(colorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
