package org.itt.minhduc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.itt.minhduc.domain.enumeration.Catalog;
import org.itt.minhduc.domain.enumeration.StatusProduct;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private String id;

    private String name;

    private String image;

    private Long price;

    private Catalog catalog;

    private Long vote;

    private Long rate;

    private Long count;

    private StatusProduct status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Long getVote() {
        return vote;
    }

    public void setVote(Long vote) {
        this.vote = vote;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public StatusProduct getStatus() {
        return status;
    }

    public void setStatus(StatusProduct status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
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
