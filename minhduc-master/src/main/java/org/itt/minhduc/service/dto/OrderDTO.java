package org.itt.minhduc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.itt.minhduc.domain.enumeration.StatusOrder;

/**
 * A DTO for the Order entity.
 */
public class OrderDTO implements Serializable {

    private String id;

    private StatusOrder status;

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
