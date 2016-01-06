package com.collectorthrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collectorthrd.domain.Condition;

/**
 * Spring Data JPA repository for the Condition entity.
 */
public interface ConditionRepository extends JpaRepository<Condition,Long> {

}
