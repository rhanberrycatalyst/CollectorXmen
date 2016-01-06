package com.collectorthrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collectorthrd.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
