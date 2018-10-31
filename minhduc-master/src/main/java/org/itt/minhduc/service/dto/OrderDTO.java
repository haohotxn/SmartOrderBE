package org.itt.minhduc.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.itt.minhduc.domain.Product;
import org.itt.minhduc.domain.enumeration.StatusOrder;
import org.itt.minhduc.domain.sub.ProductInOrder;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.util.Pair;

/**
 * A DTO for the Order entity.
 */
public class OrderDTO implements Serializable {

    private String id;

    private StatusOrder status;

    private Integer tableId;
    
    private List<Pair<Product, Integer>> products; 
    
    
    
    public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}


	public List<Pair<Product, Integer>> getProducts() {
		return products;
	}

	public void setProducts(List<Pair<Product, Integer>> products) {
		this.products = products;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
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

        OrderDTO orderDTO = (OrderDTO) o;
        if (orderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
