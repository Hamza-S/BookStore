package bean;

public class OrderBean {
	private String id;
	private String bid;
	private String title;
	private int price;
	private int quantity;
	
	public OrderBean(String id, String bid, String title, int price, int quantity) {
		this.id = id;
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public OrderBean() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
