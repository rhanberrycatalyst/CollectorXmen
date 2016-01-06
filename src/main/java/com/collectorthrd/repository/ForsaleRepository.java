package com.collectorthrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collectorthrd.domain.Forsale;

/**
 * Spring Data JPA repository for the Forsale entity.
 */
public interface ForsaleRepository extends JpaRepository<Forsale,Long> {

}
