package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.UserBean;

public class OrdersDAO {

	DataSource ds;

	public OrdersDAO() throws ClassNotFoundException {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public int InsertOrderItem(String id, String bid, String title, int price, int quantity) throws SQLException {
		String query = ("INSERT INTO ORDERITEMS values(?, ?, ?, ?, ?)");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, id);
		p.setString(2, bid);
		p.setString(3, title);
		p.setInt(4, price);
		p.setInt(5, quantity);
		int success = p.executeUpdate();
		System.out.println(query);
		p.close();
		con.close();
	
		return success;
	}
	public int InsertOrder(String id, String street, String province, String country, String zip, String billStreet, String billProvince, String billCountry, String billZip, String username, String firstName, String lastName) throws SQLException {
		String query = ("INSERT INTO ORDERS values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		System.out.println(query);
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, id);
		p.setString(2, street);
		p.setString(3, province);
		p.setString(4, country);
		p.setString(5, zip);
		p.setString(6, billStreet);
		p.setString(7, billProvince);
		p.setString(8, billCountry);
		p.setString(9, billZip);
		p.setString(10, username);
		p.setString(11, firstName);
		p.setString(12, lastName);
		System.out.println(query);
		int success = p.executeUpdate();
		
		p.close();
		con.close();
		return success;
	}
	
}
