package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import dao.BooksDAO;

public class Books {
	private static Books instance;
	private BooksDAO bDAO;
 
	private Books() {
	 
	}
	public static Books getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new Books();
			instance.bDAO = new BooksDAO();
		}
		return instance;
		
	}
	public Map<String, BookBean> getLibrary() throws SQLException {
		return bDAO.getLibrary();
	}
	public Map<String, BookBean> searchLibrary(String title) throws SQLException {
		return bDAO.searchLibrary(title);
	}
	
	public String generateBookCards(Map<String,BookBean> data) {
		String books = "";
		BookBean b = null;
		for (Map.Entry<String, BookBean> i : data.entrySet()) {
			b = i.getValue();
			books += "<div class=\"col-md-4\">";
			books += "<div class=\"card mb-4 shadow-sm\">";
			books += "<svg class=\"bd-placeholder-img card-img-top\" width=\"100%\" height=\"225\" xmlns=\"http://www.w3.org/2000/svg\"\r\n" + 
					"preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\">\r\n" + 
					"<image href=\"https://upload.wikimedia.org/wikipedia/en/c/c9/Harry_Potter_and_the_Goblet_of_Fire_Poster.jpg\"></image>\r\n" + 
					"</svg>";
			books += "<div class=\"card-body\">";
			books += "<h6 class=\"card-text\"> " + b.getTitle() + "</h6>";
			books += "<h7 class=\"card-text\"> " + b.getCategory() + "</h7>";
			books += "<br />";
			books += "<h7 class=\"card-text\"> $" + b.getPrice() + "</h7>";
			books += "<br />";
			books += "<div class=\"btn-group\">";
			books += "<form action=\"bookStore\" method=\"GET\">";
			books += "<input type=\"hidden\" name=\"bid\" value=\"" + b.getBid() + "\" />";
			books += "<button action='submit' class=\"btn btn-sm btn-outline-secondary\" name='moreInfo' value=\"true\">More Info</button>";
			books += "</form>";
			books += "</div>";
			books += "</div>";
			books += "</div>";
			books += "</div>";

		}
		return books;
		
	}
}