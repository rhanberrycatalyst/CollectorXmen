package com.collectorthrd.repository;

import com.collectorthrd.domain.Forsale;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Forsale entity.
 */
public interface ForsaleRepository extends JpaRepository<Forsale,Long> {

}
