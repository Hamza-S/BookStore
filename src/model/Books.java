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
}