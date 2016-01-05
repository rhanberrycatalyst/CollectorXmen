package com.collectorthrd.service;

import com.collectorthrd.domain.Forsale;

import java.util.List;

/**
 * Service Interface for managing Forsale.
 */
public interface ForsaleService {

    /**
     * Save a forsale.
     * @return the persisted entity
     */
    public Forsale save(Forsale forsale);

    /**
     *  get all the forsales.
     *  @return the list of entities
     */
    public List<Forsale> findAll();

    /**
     *  get the "id" forsale.
     *  @return the entity
     */
    public Forsale findOne(Long id);

    /**
     *  delete the "id" forsale.
     */
    public void delete(Long id);

    /**
     * search for the forsale corresponding
     * to the query.
     */
    public List<Forsale> search(String query);
}
