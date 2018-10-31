package org.itt.minhduc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.itt.minhduc.domain.enumeration.StatusTable;

/**
 * A DTO for the TableOrder entity.
 */
public class TableOrderDTO implements Serializable {

    private String id;

    private String name;

    private StatusTable status;

    private String accessTableCode;

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

    public StatusTable getStatus() {
        return status;
    }

    public void setStatus(StatusTable status) {
        this.status = status;
    }

    public String getAccessTableCode() {
        return accessTableCode;
    }

    public void setAccessTableCode(String accessTableCode) {
        this.accessTableCode = accessTableCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableOrderDTO tableOrderDTO = (TableOrderDTO) o;
        if (tableOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tableOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TableOrderDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", accessTableCode='" + getAccessTableCode() + "'" +
            "}";
    }
}
