package com.collectorthrd.service;

import java.util.List;

import com.collectorthrd.domain.Category;

/**
 * Service Interface for managing Category.
 */
public interface CategoryService {

    /**
     * Save a category.
     * @return the persisted entity
     */
    public Category save(Category category);

    /**
     *  get all the categorys.
     *  @return the list of entities
     */
    public List<Category> findAll();

    /**
     *  get the "id" category.
     *  @return the entity
     */
    public Category findOne(Long id);

    /**
     *  delete the "id" category.
     */
    public void delete(Long id);

    /**
     * search for the category corresponding
     * to the query.
     */
    public List<Category> search(String query);
}
