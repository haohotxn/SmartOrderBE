package org.itt.minhduc.service.dto;

import java.time.Instant;
import java.util.Set;

import org.itt.minhduc.domain.SetProductInOrder;
import org.itt.minhduc.domain.enumeration.StatusOrder;

public class OrderDTOWithTableName {

	private String id;

    private StatusOrder status;
    
    private String tableName;
   
    private String createdBy;
    
    private Instant createdDate;
    
    private String lastModifiedBy;
    
    private Instant lastModifiedDate;

    private Set<SetProductInOrder> productsInOrder;

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set<SetProductInOrder> getProductsInOrder() {
		return productsInOrder;
	}

	public void setProductsInOrder(Set<SetProductInOrder> productsInOrder) {
		this.productsInOrder = productsInOrder;
	}
    
    
}
