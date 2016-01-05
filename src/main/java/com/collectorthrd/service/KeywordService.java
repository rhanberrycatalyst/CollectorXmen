package com.collectorthrd.service;

import com.collectorthrd.domain.Keyword;

import java.util.List;

/**
 * Service Interface for managing Keyword.
 */
public interface KeywordService {

    /**
     * Save a keyword.
     * @return the persisted entity
     */
    public Keyword save(Keyword keyword);

    /**
     *  get all the keywords.
     *  @return the list of entities
     */
    public List<Keyword> findAll();

    /**
     *  get the "id" keyword.
     *  @return the entity
     */
    public Keyword findOne(Long id);

    /**
     *  delete the "id" keyword.
     */
    public void delete(Long id);

    /**
     * search for the keyword corresponding
     * to the query.
     */
    public List<Keyword> search(String query);
}
