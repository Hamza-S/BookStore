package model;

import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Books {
	public static void main(String[] args) throws SQLException {
		try {
			DataSource ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK");
			while (rs.next()) {
				String em = rs.getString("bid");
				String fname = rs.getString("title");
				System.out.println("\t" + em + ",\t" + fname + "\t ");
			} // end while loop
			con.close();
		} catch (

		NamingException e) {
			e.printStackTrace();
		}
	}
}