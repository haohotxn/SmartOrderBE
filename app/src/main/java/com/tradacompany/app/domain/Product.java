package com.tradacompany.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import com.tradacompany.app.domain.enumeration.Catalog;

import com.tradacompany.app.domain.enumeration.StatusProduct;

/**
 * A Product.
 */
@Document(collection = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("image")
    private String image;

    @Field("price")
    private Long price;

    @Field("catalog")
    private Catalog catalog;

    @Field("vote")
    private Long vote;

    @Field("rate")
    private Long rate;

    @Field("count")
    private Long count;

    @Field("status")
    private StatusProduct status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Product image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public Product price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Product catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Long getVote() {
        return vote;
    }

    public Product vote(Long vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Long vote) {
        this.vote = vote;
    }

    public Long getRate() {
        return rate;
    }

    public Product rate(Long rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Long getCount() {
        return count;
    }

    public Product count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public StatusProduct getStatus() {
        return status;
    }

    public Product status(StatusProduct status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusProduct status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", price=" + getPrice() +
            ", catalog='" + getCatalog() + "'" +
            ", vote=" + getVote() +
            ", rate=" + getRate() +
            ", count=" + getCount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
