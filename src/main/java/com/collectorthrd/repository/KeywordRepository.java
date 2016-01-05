package com.collectorthrd.repository;

import com.collectorthrd.domain.Keyword;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Keyword entity.
 */
public interface KeywordRepository extends JpaRepository<Keyword,Long> {

}
