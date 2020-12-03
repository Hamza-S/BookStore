package authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class Authenticator {
	DataSource ds;
	String seed = "amsdonasidnaoisu'na08snc0an";

	public Authenticator() {
		try {
			this.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public String cypherPassword(String password) throws NoSuchAlgorithmException {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(password);;
	
		return encryptedPassword;
	}

	public boolean authenticate(String username, String password) throws NoSuchAlgorithmException, SQLException {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		boolean authenticate = false;
		String hashedPassword = this.cypherPassword(password);
		String a = this.cypherPassword(password);
	
		String query = ("select password from users where username = '" + username + "'");
		System.out.println(query);
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		String storedPass = "";
		while (r.next()) {
			storedPass = r.getString("password");
		}
		System.out.println(storedPass);
		System.out.println(hashedPassword);
		if (passwordEncryptor.checkPassword("testing123", hashedPassword)) {
			System.out.println("welcome motherfucker");
		}
		System.out.println(a);
		if (hashedPassword.toString().contentEquals(storedPass)) {
			authenticate = true;
		}
		r.close();
		p.close();
		con.close();

		return authenticate;

	}
	public int registerUser (String fname, String lname, String username, String email, String password) throws SQLException, NoSuchAlgorithmException {
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
	public boolean userExists(String username) throws SQLException {
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
