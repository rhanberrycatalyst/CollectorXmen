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

import com.collectorthrd.domain.Condition;
import com.collectorthrd.repository.ConditionRepository;
import com.collectorthrd.repository.search.ConditionSearchRepository;
import com.collectorthrd.service.ConditionService;

/**
 * Service Implementation for managing Condition.
 */
@Service
@Transactional
public class ConditionServiceImpl implements ConditionService{

    private final Logger log = LoggerFactory.getLogger(ConditionServiceImpl.class);
    
    @Inject
    private ConditionRepository conditionRepository;
    
    @Inject
    private ConditionSearchRepository conditionSearchRepository;
    
    /**
     * Save a condition.
     * @return the persisted entity
     */
    public Condition save(Condition condition) {
        log.debug("Request to save Condition : {}", condition);
        Condition result = conditionRepository.save(condition);
        conditionSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the conditions.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Condition> findAll() {
        log.debug("Request to get all Conditions");
        List<Condition> result = conditionRepository.findAll();
        return result;
    }

    /**
     *  get one condition by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Condition findOne(Long id) {
        log.debug("Request to get Condition : {}", id);
        Condition condition = conditionRepository.findOne(id);
        return condition;
    }

    /**
     *  delete the  condition by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Condition : {}", id);
        conditionRepository.delete(id);
        conditionSearchRepository.delete(id);
    }

    /**
     * search for the condition corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Condition> search(String query) {
        
        log.debug("REST request to search Conditions for query {}", query);
        return StreamSupport
            .stream(conditionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
