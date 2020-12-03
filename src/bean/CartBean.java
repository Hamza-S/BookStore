package bean;

import java.util.HashMap;
import java.util.Map;

public class CartBean {
	Map<String, Integer> cart;
	
	public CartBean () {
		this.setCart(new HashMap<String, Integer>());
	}
	
	public void addItem(String bid, int quantity) {
		Map<String, Integer> c = this.cart;
		if (c.containsKey(bid)) {
			c.put(bid, c.get(bid) + 1); //If item already in cart just update quantity 
		}
		else {
			c.put(bid, 1);
		}
	}
	
	public void removeItem(String bid) {
		this.cart.remove(bid); //might have to error check
	}
	public void updateQuantity(String bid, int newQuantity) {
		this.cart.replace(bid, newQuantity);
	}

	public Map<String, Integer> getCart() {
		return cart; 
	}

	public void setCart(Map<String, Integer> cart) {
		this.cart = cart;
	}
	
}
