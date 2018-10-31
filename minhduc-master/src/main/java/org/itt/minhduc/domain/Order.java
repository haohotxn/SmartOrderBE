package org.itt.minhduc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import org.itt.minhduc.domain.enumeration.StatusOrder;
import org.itt.minhduc.domain.sub.ProductInOrder;

/**
 * A Order.
 */
@Document(collection = "order")
public class Order extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("status")
    private StatusOrder status;
    
    @Field("table_id")
    private Integer tableId;
    
    @Field("product_in_order")
    private Set<ProductInOrder> products; 
    
    
    public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Set<ProductInOrder> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductInOrder> products) {
		this.products = products;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public Order status(StatusOrder status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusOrder status) {
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
        Order order = (Order) o;
        if (order.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
