package com.collectorthrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.collectorthrd.domain.Collectible;

/**
 * Spring Data JPA repository for the Collectible entity.
 */
public interface CollectibleRepository extends JpaRepository<Collectible,Long> {

    @Query("select distinct collectible from Collectible collectible left join fetch collectible.colors left join fetch collectible.keyword1s left join fetch collectible.keyword2s left join fetch collectible.keyword3s")
    List<Collectible> findAllWithEagerRelationships();

    @Query("select collectible from Collectible collectible left join fetch collectible.colors left join fetch collectible.keyword1s left join fetch collectible.keyword2s left join fetch collectible.keyword3s where collectible.id =:id")
    Collectible findOneWithEagerRelationships(@Param("id") Long id);

}
