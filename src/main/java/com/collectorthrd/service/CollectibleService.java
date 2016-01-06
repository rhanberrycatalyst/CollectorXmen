package com.collectorthrd.service;

import java.util.List;

import com.collectorthrd.domain.Collectible;

/**
 * Service Interface for managing Collectible.
 */
public interface CollectibleService {

    /**
     * Save a collectible.
     * @return the persisted entity
     */
    public Collectible save(Collectible collectible);

    /**
     *  get all the collectibles.
     *  @return the list of entities
     */
    public List<Collectible> findAll();

    /**
     *  get the "id" collectible.
     *  @return the entity
     */
    public Collectible findOne(Long id);

    /**
     *  delete the "id" collectible.
     */
    public void delete(Long id);

    /**
     * search for the collectible corresponding
     * to the query.
     */
    public List<Collectible> search(String query);
}
