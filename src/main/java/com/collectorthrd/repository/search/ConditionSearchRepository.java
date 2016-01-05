package com.collectorthrd.repository.search;

import com.collectorthrd.domain.Condition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Condition entity.
 */
public interface ConditionSearchRepository extends ElasticsearchRepository<Condition, Long> {
}
