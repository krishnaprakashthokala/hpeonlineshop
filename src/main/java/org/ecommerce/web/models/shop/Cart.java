package org.ecommerce.web.models.shop;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author sergio
 */
@Component("cart")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {

	private Integer id;
	private Set<CartItem> cartItems = new HashSet();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public void addItem(CartItem item) {
		cartItems.add(item);
	}

	public void removeItem(CartItem item) {
		cartItems.remove(item);
	}

	@Override
	public String toString() {
		return "Cart{" + "id=" + id + ", cartItems=" + cartItems + '}';
	}
}
