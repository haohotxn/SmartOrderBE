package org.itt.minhduc.domain;

import java.io.Serializable;

import org.itt.minhduc.domain.enumeration.StatusSetProductInOrder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "set_product_in_order")
public class SetProductInOrder extends AbstractAuditingEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Field("status_set_product_in_order")
	private StatusSetProductInOrder statusSetProductInOrder;
	
	@Field("product")
	private Product product;
	
	@Field("quantity")
	private Integer quantity;

	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public StatusSetProductInOrder getStatusSetProductInOrder() {
		return statusSetProductInOrder;
	}

	public void setStatusSetProductInOrder(StatusSetProductInOrder statusSetProductInOrder) {
		this.statusSetProductInOrder = statusSetProductInOrder;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	
	
}
