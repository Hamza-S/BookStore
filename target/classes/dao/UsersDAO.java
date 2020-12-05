package dao;

import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jasypt.util.password.StrongPasswordEncryptor;

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
	public UserBean getUserBean(String username) throws SQLException { //Get's userbean corresponding to username from Users table
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
	
	public String cypherPassword(String password) throws NoSuchAlgorithmException { //Cypher's given password
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(password);;
		return encryptedPassword;
	}

	public boolean authenticate(String username, String password) throws NoSuchAlgorithmException, SQLException { //authenticates user entered password against cypher algo
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		boolean authenticate = false;
		String query = ("select password from users where username = '" + username + "'");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		String storedPass = "";
		while (r.next()) {
			storedPass = r.getString("password");
		}
		if (passwordEncryptor.checkPassword(password, storedPass)) {
			authenticate = true;
		}
 
		r.close();
		p.close();
		con.close();

		return authenticate;

	}
	public int registerUser (String fname, String lname, String username, String email, String password) throws SQLException, NoSuchAlgorithmException { //register a user into the website
		String hashedPassword = this.cypherPassword(password);
		String hashedP = hashedPassword.toString();
		String query = ("INSERT INTO USERS values(?, ?, ?, ?, ?)");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, fname);
		p.setString(2, lname);
		p.setString(3, username);
		p.setString(4, hashedP);
		p.setString(5, email);
		int success = p.executeUpdate();
		p.close();
		con.close();
		return success;

	}
	public boolean userExists(String username) throws SQLException { //check if user with username exists in users table
		boolean exists = false;
		String query = ("select * from users where username = '" + username + "'");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		int count = 0;
		while (r.next()) {
			count += 1;
		}
		if (count >= 1) {
			exists = true;
		}
		r.close();
		p.close();
		con.close();

		return exists;
	}
	
}
