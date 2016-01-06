package com.collectorthrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collectorthrd.domain.Keyword;

/**
 * Spring Data JPA repository for the Keyword entity.
 */
public interface KeywordRepository extends JpaRepository<Keyword,Long> {

}
