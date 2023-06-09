package org.ecommerce.web.models.shop;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.ecommerce.persistence.models.ProductLine;

/**
 * @author sergio
 */
public class CartItem implements Serializable {

	private String id;
	@NotNull(message = "{cart.item.productline.notnull}")
	private ProductLine productLine;
	@Min(value = 1, message = "{order.line.quantity.min}")
	@Max(value = 999, message = "{order.line.quantity.max}")
	private Integer quantity = 1;
	private Double totalPrice;
	private Boolean remove = false;

	public CartItem() {
	}

	public CartItem(String cartItemId, ProductLine productLine, Integer quantity, Double totalPrice) {
		this.id = cartItemId;
		this.productLine = productLine;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductLine getProductLine() {
		return productLine;
	}

	public void setProductLine(ProductLine productLine) {
		this.productLine = productLine;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getRemove() {
		return remove;
	}

	public Boolean isRemove() {
		return remove;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 47 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CartItem other = (CartItem) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CartItem{" + "id=" + id + ", productLine=" + productLine + ", quantity=" + quantity + ", totalPrice="
				+ totalPrice + '}';
	}
}
