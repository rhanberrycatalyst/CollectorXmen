package com.collectorthrd.service;

import com.collectorthrd.domain.Condition;

import java.util.List;

/**
 * Service Interface for managing Condition.
 */
public interface ConditionService {

    /**
     * Save a condition.
     * @return the persisted entity
     */
    public Condition save(Condition condition);

    /**
     *  get all the conditions.
     *  @return the list of entities
     */
    public List<Condition> findAll();

    /**
     *  get the "id" condition.
     *  @return the entity
     */
    public Condition findOne(Long id);

    /**
     *  delete the "id" condition.
     */
    public void delete(Long id);

    /**
     * search for the condition corresponding
     * to the query.
     */
    public List<Condition> search(String query);
}
