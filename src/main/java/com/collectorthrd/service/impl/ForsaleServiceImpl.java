package com.collectorthrd.service.impl;

import com.collectorthrd.service.ForsaleService;
import com.collectorthrd.domain.Forsale;
import com.collectorthrd.repository.ForsaleRepository;
import com.collectorthrd.repository.search.ForsaleSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Forsale.
 */
@Service
@Transactional
public class ForsaleServiceImpl implements ForsaleService{

    private final Logger log = LoggerFactory.getLogger(ForsaleServiceImpl.class);
    
    @Inject
    private ForsaleRepository forsaleRepository;
    
    @Inject
    private ForsaleSearchRepository forsaleSearchRepository;
    
    /**
     * Save a forsale.
     * @return the persisted entity
     */
    public Forsale save(Forsale forsale) {
        log.debug("Request to save Forsale : {}", forsale);
        Forsale result = forsaleRepository.save(forsale);
        forsaleSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the forsales.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Forsale> findAll() {
        log.debug("Request to get all Forsales");
        List<Forsale> result = forsaleRepository.findAll();
        return result;
    }

    /**
     *  get one forsale by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Forsale findOne(Long id) {
        log.debug("Request to get Forsale : {}", id);
        Forsale forsale = forsaleRepository.findOne(id);
        return forsale;
    }

    /**
     *  delete the  forsale by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Forsale : {}", id);
        forsaleRepository.delete(id);
        forsaleSearchRepository.delete(id);
    }

    /**
     * search for the forsale corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Forsale> search(String query) {
        
        log.debug("REST request to search Forsales for query {}", query);
        return StreamSupport
            .stream(forsaleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
