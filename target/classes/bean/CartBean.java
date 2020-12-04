package bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dao.BooksDAO;

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
	public void clearCart() {
		cart.clear();
	}
	public void updateQuantity(String bid, int newQuantity) {
		this.cart.replace(bid, newQuantity);
		if (newQuantity == 0) {
			this.removeItem(bid);
		}
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
	
	public int getCartPrice() throws ClassNotFoundException {
		BooksDAO bd = new BooksDAO();
		int totalCartPrice = 0;
		for (Map.Entry<String, Integer> entry : this.cart.entrySet()) {
			try {
				BookBean book = bd.getBookById(entry.getKey());
				totalCartPrice += book.getPrice() * entry.getValue();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return totalCartPrice;
	}
	
	
	public String generateCartHTML() throws ClassNotFoundException {
		BooksDAO bd = new BooksDAO();
		String cartString = "";
		cartString += "<div class=\"py-5 text-center\"> <form class=\"update\" action=\"/BookStore/bookStore\" method=\"POST\">";
		cartString += "<div class=\"col-md-6 order-md-2 mb-6\"";
		cartString += "style=\"margin: auto; width: 50%;\">";
		cartString += "<h4 class=\"d-flex justify-content-between align-items-center mb-3\">";
		cartString += "<span class=\"text-muted\">Your cart</span> ";
		cartString += "<span class=\"badge badge-secondary badge-pill\">";
		cartString += this.getTotalQuantity();
		cartString += "</span> </h4>";
		cartString += "<ul class=\"list-group mb-3\">";
		
		//Generate li items from cart
		int orderCount = 0;
		for (Map.Entry<String, Integer> entry : this.cart.entrySet()) {
			orderCount += 1;
			try {
				String currentbid = entry.getKey().toString();
				int currentQuant = entry.getValue();
				if (currentQuant > 0) {
					BookBean book = bd.getBookById(currentbid);
					cartString += "<li class=\"list-group-item d-flex justify-content-between lh-condensed\">";
					cartString += "<div style=\"\"> <h6 class=\"my-0\">";
					cartString += "<div id=\"item-" + orderCount + "\">" + book.getTitle() + "   <i>$" +book.getPrice() + "</i></div>";
					cartString += "</h6> <span class=\"text-muted\"><div id=\"item-" + orderCount + "-value\" >";
					cartString += "</div></span></div>";
					cartString += "<div class=\"col-md-3 mb-3\"> <div id=\"item-" + orderCount + "-quantity\">";
					cartString += "<input name=\"item" + orderCount + "quant\" type=\"number\" class=\"form-control\"";
					cartString += "id=\"itemquant\" value=\"" + entry.getValue()+ "\" min=\"0\" max=\"100\"> </input></div></div></li>";
					cartString += "<input name=\"bid" + orderCount + "quant\" type=\"hidden\" value=\"" + book.getBid()
							+ "\"</input>";
				}
         
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (orderCount > 0) {
			cartString += "<li class=\"list-group-item d-flex justify-content-between\"><span>Total </span> <strong><div id=\"total\">$" + this.getCartPrice() +"</div></strong></li></ul>";
			cartString += "<button class=\"btn btn-primary btn-lg btn-block btn-dark\"  id=\"update\" name=\"updateCart\" value=\"true\" type='submit'>Update</button> ";
			cartString += "<button class=\"btn btn-primary btn-lg btn-block\" id=\"checkout\" name=\"checkout\" value=\"true\" type='submit'>Continue to checkout</button></a></form>";
			
		}
		else {
			cartString += "You have no items in your cart!";
		}
			
		cartString += "</div></div>";
		
				
		return cartString;
	}
	
	public String generateCheckoutCartHTML() throws ClassNotFoundException {
		BooksDAO bd = new BooksDAO();
		String cartString = "";
		cartString += "<div class=\"col-md-4 order-md-2 mb-4\">";
		cartString += "<h4 class=\"d-flex justify-content-between align-items-center mb-3\">";
		cartString += "<span class=\"text-muted\">Your cart</span> ";
		cartString += "<span class=\"badge badge-secondary badge-pill\">";
		cartString += this.getTotalQuantity();
		cartString += "</span> </h4>";
		cartString += "<ul class=\"list-group mb-3\">";
		
		//Generate li items from cart
		int orderCount = 0;
		for (Map.Entry<String, Integer> entry : this.cart.entrySet()) {
			orderCount += 1;
			try {
				String currentbid = entry.getKey().toString();
				int currentQuant = entry.getValue();
				if (currentQuant > 0) {
					BookBean book = bd.getBookById(currentbid);
					cartString += "<li class=\"list-group-item d-flex justify-content-between lh-condensed\">";
					cartString += "<div style=\"\"> <h6 class=\"my-0\">";
					cartString += "<div id=\"item-" + orderCount + "\">" + book.getTitle() + "</div>";
					cartString += "</h6> <span class=\"text-muted\"><div id=\"item-" + orderCount + "-value\">$";
					cartString += book.getPrice() + "</div></span></div>";
					cartString += "<div class=\"col-md-3 mb-3\"> <div id=\"item-" + orderCount + "-quantity\">";
					cartString += "<input name=\"item" + orderCount + "quant\" type=\"text\" class=\"form-control\"";
					cartString += "id=\"item" + orderCount + "quant\" value=\"" + entry.getValue()
							+ "\"readonly> </input></div></div></li>";
					cartString += "<input name=\"bid" + orderCount + "quant\" type=\"hidden\" value=\"" + book.getBid()
							+ "\"</input>";
				}
				
         
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		cartString += "<li class=\"list-group-item d-flex justify-content-between\"><span>Total </span> <strong><div id=\"total\">$" + this.getCartPrice() +"</div></strong></li></ul>";	
		cartString += "</div>";
		
				
		return cartString;
	}
}
