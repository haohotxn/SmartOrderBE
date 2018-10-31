package org.itt.minhduc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import org.itt.minhduc.domain.enumeration.StatusTable;

/**
 * A TableOrder.
 */
@Document(collection = "table_order")
public class TableOrder extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("status")
    private StatusTable status;

    @Field("access_table_code")
    private String accessTableCode;

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

    public TableOrder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusTable getStatus() {
        return status;
    }

    public TableOrder status(StatusTable status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusTable status) {
        this.status = status;
    }

    public String getAccessTableCode() {
        return accessTableCode;
    }

    public TableOrder accessTableCode(String accessTableCode) {
        this.accessTableCode = accessTableCode;
        return this;
    }

    public void setAccessTableCode(String accessTableCode) {
        this.accessTableCode = accessTableCode;
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
        TableOrder tableOrder = (TableOrder) o;
        if (tableOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tableOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TableOrder{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", accessTableCode='" + getAccessTableCode() + "'" +
            "}";
    }
}
