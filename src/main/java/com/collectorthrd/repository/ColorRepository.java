package com.collectorthrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collectorthrd.domain.Color;

/**
 * Spring Data JPA repository for the Color entity.
 */
public interface ColorRepository extends JpaRepository<Color,Long> {

}
