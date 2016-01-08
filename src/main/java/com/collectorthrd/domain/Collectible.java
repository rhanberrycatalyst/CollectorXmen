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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Collectible.
 */
@Entity
@Table(name = "collectible")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collectible")
public class Collectible implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Min(value = 0)
    @Column(name = "age", nullable = false)
    private Double age;

    @NotNull
    @Size(max = 16)
    @Pattern(regexp = "[A-Z]{3}-[0-9]{12}$")
    @Column(name = "cataloguenumber", length = 16, nullable = false, unique = true)
    private String cataloguenumber;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "collectible_color",
               joinColumns = @JoinColumn(name="collectibles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="colors_id", referencedColumnName="ID"))
    private Set<Color> colors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "collectible_keyword1",
               joinColumns = @JoinColumn(name="collectibles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="keyword1s_id", referencedColumnName="ID"))
    private Set<Keyword> keyword1s = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "collectible_keyword2",
               joinColumns = @JoinColumn(name="collectibles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="keyword2s_id", referencedColumnName="ID"))
    private Set<Keyword> keyword2s = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "collectible_keyword3",
               joinColumns = @JoinColumn(name="collectibles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="keyword3s_id", referencedColumnName="ID"))
    private Set<Keyword> keyword3s = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "forsale_id")
    private Forsale forsale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public String getCataloguenumber() {
        return cataloguenumber;
    }

    public void setCataloguenumber(String cataloguenumber) {
        this.cataloguenumber = cataloguenumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public void setColors(Set<Color> colors) {
        this.colors = colors;
    }

    public Set<Keyword> getKeyword1s() {
        return keyword1s;
    }

    public void setKeyword1s(Set<Keyword> keywords) {
        this.keyword1s = keywords;
    }

    public Set<Keyword> getKeyword2s() {
        return keyword2s;
    }

    public void setKeyword2s(Set<Keyword> keywords) {
        this.keyword2s = keywords;
    }

    public Set<Keyword> getKeyword3s() {
        return keyword3s;
    }

    public void setKeyword3s(Set<Keyword> keywords) {
        this.keyword3s = keywords;
    }

    public Forsale getForsale() {
        return forsale;
    }

    public void setForsale(Forsale forsale) {
        this.forsale = forsale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Collectible collectible = (Collectible) o;
        return Objects.equals(id, collectible.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Collectible{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", age='" + age + "'" +
            ", cataloguenumber='" + cataloguenumber + "'" +
            '}';
    }
}
