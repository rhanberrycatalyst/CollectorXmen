package com.collectorthrd.service;

import java.util.List;

import com.collectorthrd.domain.Color;

/**
 * Service Interface for managing Color.
 */
public interface ColorService {

    /**
     * Save a color.
     * @return the persisted entity
     */
    public Color save(Color color);

    /**
     *  get all the colors.
     *  @return the list of entities
     */
    public List<Color> findAll();

    /**
     *  get the "id" color.
     *  @return the entity
     */
    public Color findOne(Long id);

    /**
     *  delete the "id" color.
     */
    public void delete(Long id);

    /**
     * search for the color corresponding
     * to the query.
     */
    public List<Color> search(String query);
}
