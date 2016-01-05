package com.collectorthrd.repository.search;

import com.collectorthrd.domain.Keyword;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Keyword entity.
 */
public interface KeywordSearchRepository extends ElasticsearchRepository<Keyword, Long> {
}
