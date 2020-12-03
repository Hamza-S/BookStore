package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class BooksDAO {

	DataSource ds;

	public BooksDAO() throws ClassNotFoundException {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public Map<String, BookBean> getLibrary() throws SQLException {
		String query = ("select * from book");
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String name = r.getString("title");
			rv.put(name, new BookBean(r.getString("bid"), r.getString("title"), r.getString("category"), Integer.parseInt(r.getString("price"))));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	public Map<String, BookBean> searchLibrary(String bookTitle) throws SQLException {
		String t = bookTitle.toLowerCase();
		String query = ("select * from book WHERE lower(title) like '%" + t + "%'");
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String name = r.getString("title");
			rv.put(name, new BookBean(r.getString("bid"), r.getString("title"), r.getString("category"), Integer.parseInt(r.getString("price"))));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
	public BookBean getBookById(String bid) throws SQLException {
		String query = ("select * from book where bid like '%" + bid + "%'");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		BookBean book=null;
		int size=r.getFetchSize();
		while (r.next()) {
			book = new BookBean(r.getString("bid"), r.getString("title"), r.getString("category"), Integer.parseInt(r.getString("price")));
		}
		r.close();
		p.close();
		con.close();
		return book;
	}
	
	
	public Map<String, BookBean> getBooksByCategory(String category) throws SQLException {
		String c = category.toLowerCase();
		String query = ("select * from book WHERE lower(category) = '" + c + "'");
		if (category.contentEquals("all")) {
			query = ("select * from book");
		}
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String name = r.getString("title");
			rv.put(name, new BookBean(r.getString("bid"), r.getString("title"), r.getString("category"), Integer.parseInt(r.getString("price"))));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}
