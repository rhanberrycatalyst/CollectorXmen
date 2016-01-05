package com.collectorthrd.service.impl;

import com.collectorthrd.service.CategoryService;
import com.collectorthrd.domain.Category;
import com.collectorthrd.repository.CategoryRepository;
import com.collectorthrd.repository.search.CategorySearchRepository;
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
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    
    @Inject
    private CategoryRepository categoryRepository;
    
    @Inject
    private CategorySearchRepository categorySearchRepository;
    
    /**
     * Save a category.
     * @return the persisted entity
     */
    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
        Category result = categoryRepository.save(category);
        categorySearchRepository.save(result);
        return result;
    }

    /**
     *  get all the categorys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Category> findAll() {
        log.debug("Request to get all Categorys");
        List<Category> result = categoryRepository.findAll();
        return result;
    }

    /**
     *  get one category by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Category findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        return category;
    }

    /**
     *  delete the  category by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
        categorySearchRepository.delete(id);
    }

    /**
     * search for the category corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Category> search(String query) {
        
        log.debug("REST request to search Categorys for query {}", query);
        return StreamSupport
            .stream(categorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
