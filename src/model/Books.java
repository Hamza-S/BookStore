package model;

import java.security.NoSuchAlgorithmException;


import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import bean.BookBean;
import bean.OrderBean;
import bean.ReviewBean;
import bean.UserBean;
import dao.AddressDAO;
import dao.BooksDAO;
import dao.UsersDAO;
import dao.reviewDAO;
import dao.OrdersDAO;

public class Books {
	private static Books instance;
	private BooksDAO bDAO;
	private UsersDAO uDAO;
	private AddressDAO aDAO;
	private reviewDAO rDAO;
	private OrdersDAO oDAO;
 
	private Books() {
	 
	}
	public static Books getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new Books();
			instance.bDAO = new BooksDAO();
			instance.uDAO = new UsersDAO();
			instance.aDAO = new AddressDAO();
			instance.rDAO= new reviewDAO();
			instance.oDAO = new OrdersDAO();
		}
		return instance;
		
	}
	public Map<String, BookBean> getLibrary() throws SQLException {
		return bDAO.getLibrary();
	}
	public Map<String, BookBean> searchLibrary(String title) throws SQLException {
		return bDAO.searchLibrary(title);
	}
	public Map<String, BookBean> getBooksByCategory(String category) throws SQLException {
		return bDAO.getBooksByCategory(category);
	}
	
	//Sanitize inputs
    public String sanitizeQuery(String s) {
        s = s.replaceAll("%", "\\%")
                .replaceAll("\"", "\\\"")
                .replaceAll("'", "\\'")
                .replaceAll("_","\\_")
                .replaceAll("<", "&lt")
                .replaceAll(">", "&gt");
        return s;
        
    }
    
    //all inputs in methods below (that work with DAO for db manipulation) are sanitized before processing sql request
	public boolean registerUser (String fname, String lname, String username, String email, String password) throws NoSuchAlgorithmException, SQLException {
		boolean registered = false;
		fname = sanitizeQuery (fname);
		lname = sanitizeQuery (lname);
		username = sanitizeQuery (username);
		email = sanitizeQuery (email);
		password = sanitizeQuery (password);
		if (uDAO.userExists(username)) {
			//throw error because user already exists
		}
		else {
			uDAO.registerUser(fname, lname, username, email, password);
			registered = true;
		}
		return registered;
	}
	
	public int insertAddress(String username, String address, String province, String country, String zip) throws SQLException {
		username = sanitizeQuery (username);
		address = sanitizeQuery (address);
		province = sanitizeQuery (province);
		country = sanitizeQuery (country);
		zip = sanitizeQuery (zip);
		return aDAO.insertAddress(username, address, country, province, zip);
		
	}
	
	public String getAddressAttribute(String username, String attribute) throws SQLException {
		username = sanitizeQuery (username);
		attribute = sanitizeQuery (attribute);
		return aDAO.getAddressAttribute(username, attribute);
		
	}
	
	public boolean login(String username, String password) throws NoSuchAlgorithmException, SQLException {
		boolean authenticated = false;
		if (!uDAO.userExists(username)) {
			System.out.println("the username doesnt exist");
			//throw error because USER does not exist
		}
		else {
			
			authenticated = uDAO.authenticate(username, password);
		}
		return authenticated;
	}
	
	public UserBean getUserBean(String username) throws SQLException {
		return uDAO.getUserBean(username);
		
	}
	
	public String export_json(String bid) throws SQLException {
		//get book by bid -> convert into json -> return json
		bid = sanitizeQuery (bid);
		BookBean book=bDAO.getBookById(bid);
		JSONObject jsonObj = new JSONObject(book);
		String json = jsonObj.toString(4);
		return json;
		
	}

	public String generateBookCards(Map<String,BookBean> data) {
		String books = "";
		BookBean b = null;
		for (Map.Entry<String, BookBean> i : data.entrySet()) {
			b = i.getValue();
			books += "<div class=\"col-md-4\">";
			books += "<div class=\"card mb-4 shadow-sm\">";
		
			books += "<img class=\"card-img-top\" src=\"/BookStore/res/img/" + b.getBid() + ".jpg\">";
			books += "<div class=\"card-body\" id=\"wrapper\" >";
			books += "<center>";
			books += "<h6 class=\"card-text font-weight-bold\"> " + b.getTitle() + "</h6>";
			books += "</center>";
			books += "<h7 class=\"card-text \"> " + b.getCategory() + "</h7>";
			books += "<br />";
			books += "<h7 class=\"card-text h5\" id=\"price\"> $" + b.getPrice() + "</h7>";
			books += "<br />";
			books += "<div class=\"btn-group\">";
			books += "<form action=\"bookStore\" method=\"GET\">";
			books += "<input type=\"hidden\" name=\"bid\" value=\"" + b.getBid() + "\" />";
			books += "<button action='submit' class=\"btn btn-sm btn btn-primary \" name='moreInfo' value=\"true\">More Info</button>";
			books += "</form>";
			books += "</div>";
			books += "</div>";
			books += "</div>";
			books += "</div>";

		}
		return books;
		
	}
	
	public int InsertOrderItem(String id, String bid, String title, int price, int quantity) throws SQLException {
		id = sanitizeQuery (id);
		bid = sanitizeQuery (bid);
		title = sanitizeQuery (title);
		return oDAO.InsertOrderItem(id, bid, title, price, quantity);
	}
	public int InsertOrder(String id, String street, String province, String country, String zip, String billStreet, String billProvince, String billCountry, String billZip, String username, String firstName, String lastName, String date) throws SQLException {
		id = sanitizeQuery (id);
		street = sanitizeQuery (street);
		province = sanitizeQuery (province);
		country = sanitizeQuery (country);
		zip = sanitizeQuery (zip);
		billStreet = sanitizeQuery (billStreet);
		billProvince = sanitizeQuery (billProvince);
		billCountry = sanitizeQuery (billCountry);
		billZip = sanitizeQuery (billZip);
		username = sanitizeQuery (username);
		firstName = sanitizeQuery (firstName);
		lastName = sanitizeQuery (lastName);
		date = sanitizeQuery (date);
		
		return oDAO.InsertOrder(id, street, province, country, zip, billStreet, billProvince, billCountry, billZip, username, firstName, lastName, date);
	}
	
	public BookBean getBook(String bid) throws SQLException {
		bid = sanitizeQuery (bid);
		BookBean book=bDAO.getBookById(bid);
		return book;
	}
	
	public ArrayList<OrderBean> getOrdersByMonth(String month) throws SQLException {
		return oDAO.getOrdersByMonth(month);
	}
	
	public int addReview(String username, String bid, String title, String review, String rating)throws SQLException{
		username = sanitizeQuery (username);
		bid = sanitizeQuery (bid);
		title = sanitizeQuery (title);
		review = sanitizeQuery (review);
		rating = sanitizeQuery (rating);
		return rDAO.addReview(username, bid, title, review, rating);
	}
	public boolean userReviewedTheBook(String username, String bid)throws SQLException{
		return rDAO.userReviewedTheBook(username, bid);
	}
	public ArrayList<ReviewBean>  getBookReviews(String bid)throws SQLException{
		return rDAO.getBookReviews(bid);
	}
	
	public String getMonth(String month) {
		String monthString;
		switch (month) {
          case "01":  monthString = "January";
                   break;
          case "02":  monthString = "February";
                   break;
          case "03":  monthString = "March";
                   break;
          case "04":  monthString = "April";
                   break;
          case "05":  monthString = "May";
                   break;
          case "06":  monthString = "June";
                   break;
          case "07":  monthString = "July";
                   break;
          case "08":  monthString = "August";
                   break;
          case "09":  monthString = "September";
                   break;
          case "10": monthString = "October";
                   break;
          case "11": monthString = "November";
                   break;
          case "12": monthString = "December";
                   break;
          default: monthString = "Invalid month";
                   break;
   	
	}
		return monthString;
	}
	public String generateReviewHTML(String bid) throws SQLException {
		return rDAO.generateReviewHTML(bid);
	}
	public Map<String, Long> getReviewStats(String bid) throws SQLException {
		return rDAO.generateReviewStats(bid);
	}
	
	public String getBookOrdersById(String bid) throws SQLException {
		Map<String,OrderBean> orders = oDAO.getOrderItemsByBID(bid);
		System.out.println(orders.size());
		String json = "";
		for (Map.Entry<String, OrderBean> entry : orders.entrySet()) {
			System.out.println("YOOOO");
			System.out.println(entry.getValue().getId());	
			JSONObject jsonObj = new JSONObject(entry.getValue());
			json += jsonObj.toString(4);
		
		}
		return json;
	}
	

}