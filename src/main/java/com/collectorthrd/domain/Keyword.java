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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Keyword.
 */
@Entity
@Table(name = "keyword")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "keyword")
public class Keyword implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "keyword", length = 50, nullable = false)
    private String keyword;

    @ManyToMany(mappedBy = "keyword1s")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Collectible> keyword1s = new HashSet<>();

    @ManyToMany(mappedBy = "keyword2s")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Collectible> keyword2s = new HashSet<>();

    @ManyToMany(mappedBy = "keyword3s")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Collectible> keyword3s = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Set<Collectible> getKeyword1s() {
        return keyword1s;
    }

    public void setKeyword1s(Set<Collectible> collectibles) {
        this.keyword1s = collectibles;
    }

    public Set<Collectible> getKeyword2s() {
        return keyword2s;
    }

    public void setKeyword2s(Set<Collectible> collectibles) {
        this.keyword2s = collectibles;
    }

    public Set<Collectible> getKeyword3s() {
        return keyword3s;
    }

    public void setKeyword3s(Set<Collectible> collectibles) {
        this.keyword3s = collectibles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Keyword keyword = (Keyword) o;
        return Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Keyword{" +
            "id=" + id +
            ", keyword='" + keyword + "'" +
            '}';
    }
}
