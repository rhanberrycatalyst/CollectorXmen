package com.collectorthrd.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Forsale.
 */
@Entity
@Table(name = "forsale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "forsale")
public class Forsale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "forsale", nullable = false)
    private Boolean forsale;

    @OneToMany(mappedBy = "forsale")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Collectible> collectibles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getForsale() {
        return forsale;
    }

    public void setForsale(Boolean forsale) {
        this.forsale = forsale;
    }

    public Set<Collectible> getCollectibles() {
        return collectibles;
    }

    public void setCollectibles(Set<Collectible> collectibles) {
        this.collectibles = collectibles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Forsale forsale = (Forsale) o;
        return Objects.equals(id, forsale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Forsale{" +
            "id=" + id +
            ", forsale='" + forsale + "'" +
            '}';
    }
}
