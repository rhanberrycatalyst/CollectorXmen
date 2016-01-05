package com.collectorthrd.repository.search;

import com.collectorthrd.domain.Forsale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Forsale entity.
 */
public interface ForsaleSearchRepository extends ElasticsearchRepository<Forsale, Long> {
}
