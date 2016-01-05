package com.collectorthrd.repository;

import com.collectorthrd.domain.Color;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Color entity.
 */
public interface ColorRepository extends JpaRepository<Color,Long> {

}
