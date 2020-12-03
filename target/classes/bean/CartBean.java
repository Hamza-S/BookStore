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
			c.put(bid, c.get(bid)+quantity);
		}
		else {
			c.put(bid, quantity);
		}
		
	}
	
	public void removeItem(String bid) {
		this.cart.remove(bid); //might have to error check
	}
	public void updateQuantity(String bid, int newQuantity) {
		this.cart.replace(bid, newQuantity);
	}

	public Map<String, Integer> getCart() {
		return this.cart; 
	}
	
	public int getTotalQuantity() {
		int totalQuant = 0;
		if (this.cart.size() > 0) {
			for (Integer value : this.cart.values()) {
			    totalQuant += value;
			}
		}
		return totalQuant;
	}
	public void setCart(Map<String, Integer> cart) {
		this.cart = cart;
	}
	
}
