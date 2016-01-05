package com.collectorthrd.repository.search;

import com.collectorthrd.domain.Collectible;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Collectible entity.
 */
public interface CollectibleSearchRepository extends ElasticsearchRepository<Collectible, Long> {
}
