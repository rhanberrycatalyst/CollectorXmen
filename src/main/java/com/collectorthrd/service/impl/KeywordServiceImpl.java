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

import com.collectorthrd.domain.Keyword;
import com.collectorthrd.repository.KeywordRepository;
import com.collectorthrd.repository.search.KeywordSearchRepository;
import com.collectorthrd.service.KeywordService;

/**
 * Service Implementation for managing Keyword.
 */
@Service
@Transactional
public class KeywordServiceImpl implements KeywordService{

    private final Logger log = LoggerFactory.getLogger(KeywordServiceImpl.class);
    
    @Inject
    private KeywordRepository keywordRepository;
    
    @Inject
    private KeywordSearchRepository keywordSearchRepository;
    
    /**
     * Save a keyword.
     * @return the persisted entity
     */
    public Keyword save(Keyword keyword) {
        log.debug("Request to save Keyword : {}", keyword);
        Keyword result = keywordRepository.save(keyword);
        keywordSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the keywords.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Keyword> findAll() {
        log.debug("Request to get all Keywords");
        List<Keyword> result = keywordRepository.findAll();
        return result;
    }

    /**
     *  get one keyword by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Keyword findOne(Long id) {
        log.debug("Request to get Keyword : {}", id);
        Keyword keyword = keywordRepository.findOne(id);
        return keyword;
    }

    /**
     *  delete the  keyword by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Keyword : {}", id);
        keywordRepository.delete(id);
        keywordSearchRepository.delete(id);
    }

    /**
     * search for the keyword corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Keyword> search(String query) {
        
        log.debug("REST request to search Keywords for query {}", query);
        return StreamSupport
            .stream(keywordSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
