package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.ReviewBean;
import bean.UserBean;

public class reviewDAO {
	DataSource ds;
	public reviewDAO () throws ClassNotFoundException {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int addReview(String username, String bid, String title, String review, String rating) throws SQLException {
		String query = ("INSERT INTO  REVIEW values(?, ?, ?, ?, ?)");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, username);
		p.setString(2, bid);
		p.setString(3, title);
		p.setString(4, review);
		int ratingVal= Integer.parseInt(rating);
		p.setInt(5, ratingVal);
		System.out.println(query);
		int success = p.executeUpdate();
		System.out.println("sucess:"+success);

//		insert into Review (username, bid, title, text, rating) VALUES ('syed001','b001','This book is Ass','I cant read tbh.', 4);

		p.close();
		con.close();
		return success;
	}
	public boolean userReviewedTheBook(String username, String bid) throws SQLException {
		String query = ("select * from REVIEW where BID = '" + bid + "' and USERNAME = '"+username+"'");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		int size=r.getFetchSize();
		r.close();
		p.close();
		con.close();
		if(size>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public  ArrayList<ReviewBean> getBookReviews( String bid) throws SQLException {
		String query = ("select * from REVIEW where BID = '" + bid + "'");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		ArrayList<ReviewBean> reviews=new ArrayList<ReviewBean>();
		while (r.next()) {
			ReviewBean review= 
					new ReviewBean(r.getString("username"), r.getString("bid"), r.getString("title"), r.getString("text"), r.getInt("rating"));
			reviews.add(review);
		}
		r.close();
		p.close();
		con.close();

		return reviews;
	}

}
