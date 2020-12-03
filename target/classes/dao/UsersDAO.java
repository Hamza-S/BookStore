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

public class UsersDAO {

	DataSource ds;

	public UsersDAO() throws ClassNotFoundException {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public UserBean getUserBean(String username) throws SQLException {
		String query = ("select * from users where username = '" + username + "'");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		UserBean user = null;
		while (r.next()) {
			user = new UserBean(r.getString("firstName"), r.getString("lastName"), r.getString("username"), r.getString("email"));
		}
		
		r.close();
		p.close();
		con.close();

		return user;
	}
	
}
