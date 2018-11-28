package org.itt.minhduc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import org.itt.minhduc.domain.TableBooking;
import org.itt.minhduc.domain.enumeration.StatusTable;

/**
 * A DTO for the TableOrder entity.
 */
public class TableOrderDTO implements Serializable {

    private String id;

    private String name;

    private StatusTable status;

    private String accessTableCode;

    private String guestRequest;
    
    private String detail;

    private String linkToTable;
    
    private Set<TableBooking> tableBooking;
    
    

	public Set<TableBooking> getTableBooking() {
		return tableBooking;
	}

	public void setTableBooking(Set<TableBooking> tableBooking) {
		this.tableBooking = tableBooking;
	}

	public String getLinkToTable() {
		return linkToTable;
	}

	public void setLinkToTable(String linkToTable) {
		this.linkToTable = linkToTable;
	}

	public String getGuestRequest() {
		return guestRequest;
	}

	public void setGuestRequest(String guestRequest) {
		this.guestRequest = guestRequest;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

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
