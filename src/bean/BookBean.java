package bean;

public class BookBean {
	private String bid;
	private String title;
	private String category;
	private int price;
	
	public BookBean(String bid, String title, String category, int price) {
		this.setBid(bid);
		this.setTitle(title);
		this.setCategory(category);
		this.setPrice(price);
	}
	public BookBean() {
		
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
