package com.collectorthrd.service.impl;

import com.collectorthrd.service.CollectibleService;
import com.collectorthrd.domain.Collectible;
import com.collectorthrd.repository.CollectibleRepository;
import com.collectorthrd.repository.search.CollectibleSearchRepository;
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
 * Service Implementation for managing Collectible.
 */
@Service
@Transactional
public class CollectibleServiceImpl implements CollectibleService{

    private final Logger log = LoggerFactory.getLogger(CollectibleServiceImpl.class);
    
    @Inject
    private CollectibleRepository collectibleRepository;
    
    @Inject
    private CollectibleSearchRepository collectibleSearchRepository;
    
    /**
     * Save a collectible.
     * @return the persisted entity
     */
    public Collectible save(Collectible collectible) {
        log.debug("Request to save Collectible : {}", collectible);
        Collectible result = collectibleRepository.save(collectible);
        collectibleSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the collectibles.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Collectible> findAll() {
        log.debug("Request to get all Collectibles");
        List<Collectible> result = collectibleRepository.findAllWithEagerRelationships();
        return result;
    }

    /**
     *  get one collectible by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Collectible findOne(Long id) {
        log.debug("Request to get Collectible : {}", id);
        Collectible collectible = collectibleRepository.findOneWithEagerRelationships(id);
        return collectible;
    }

    /**
     *  delete the  collectible by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Collectible : {}", id);
        collectibleRepository.delete(id);
        collectibleSearchRepository.delete(id);
    }

    /**
     * search for the collectible corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Collectible> search(String query) {
        
        log.debug("REST request to search Collectibles for query {}", query);
        return StreamSupport
            .stream(collectibleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
