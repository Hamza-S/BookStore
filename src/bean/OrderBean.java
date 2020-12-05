package bean;

public class OrderBean {
	private String id;
	private String bid;
	private String title;
	private int price;
	private int quantity;	
	private String street;
	private String province;
	private String country;
	private String zip;
	private String streetBill;
	private String provinceBill;
	private String countryBill;
	private String zipBill;
	private String username;
	private String firstname;
	private String lastname;
	private String date;
	
	
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
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStreetBill() {
		return streetBill;
	}
	public void setStreetBill(String streetBill) {
		this.streetBill = streetBill;
	}
	public String getProvinceBill() {
		return provinceBill;
	}
	public void setProvinceBill(String provinceBill) {
		this.provinceBill = provinceBill;
	}
	public String getCountryBill() {
		return countryBill;
	}
	public void setCountryBill(String countryBill) {
		this.countryBill = countryBill;
	}
	public String getZipBill() {
		return zipBill;
	}
	public void setZipBill(String zipBill) {
		this.zipBill = zipBill;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	
}
