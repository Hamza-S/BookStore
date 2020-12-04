package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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

}
