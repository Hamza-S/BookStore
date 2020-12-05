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
import bean.UserBean;

public class AddressDAO {

	DataSource ds;

	public AddressDAO() throws ClassNotFoundException {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public int insertAddress(String username, String address, String country, String province, String zip) throws SQLException { //Inserts the address into address table
		String query = ("INSERT INTO ADDRESS values(?, ?, ?, ?, ?)");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, address);
		p.setString(2, province);
		p.setString(3, country);
		p.setString(4, zip);
		p.setString(5, username);
		int success = p.executeUpdate();
		p.close();
		con.close();
		return success;
	}
	
	public String getAddressAttribute(String username, String attribute) throws SQLException { //get attributes from address table for a certain user
		String query = ("SELECT " + attribute + " FROM ADDRESS WHERE username LIKE '" + username + "'" );
		String result = "";
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()){
			result = r.getString(attribute);
		}
		r.close();
		p.close();
		con.close();
		return result;
	}
	
}
