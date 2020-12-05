package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.OrderBean;
import bean.BookBean;
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
	public int InsertOrderItem(String id, String bid, String title, int price, int quantity) throws SQLException { //Insert order item into orderitems table
		String query = ("INSERT INTO ORDERITEMS values(?, ?, ?, ?, ?)");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.setString(1, id);
		p.setString(2, bid);
		p.setString(3, title);
		p.setInt(4, price);
		p.setInt(5, quantity);
		int success = p.executeUpdate();
		p.close();
		con.close();
	
		return success;
	}
	public int InsertOrder(String id, String street, String province, String country, String zip, String billStreet, String billProvince, String billCountry, String billZip, String username, String firstName, String lastName, String date) throws SQLException {
		//Insert an order into the orders table
		String query = ("INSERT INTO ORDERS values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
		p.setString(13,  date);
		int success = p.executeUpdate();
		
		p.close();
		con.close();
		if (success == 1) {
			
		}
		return success;
	}
	
	public ArrayList<OrderBean> getOrdersByMonth(String month) throws SQLException { //get all orders in a given month
		String query = ("select * from orderitems where id in (select id from orders where date like '%-" + month + "-%')");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		OrderBean order=null;
		ArrayList<OrderBean> ordersinMonth= new ArrayList<OrderBean>();
		while (r.next()) {
			order = new OrderBean(r.getString("id"), r.getString("bid"), r.getString("name"), r.getInt("price"), r.getInt("quantity"));
			ordersinMonth.add(order);
		}
		r.close();
		p.close();
		con.close();
		return ordersinMonth;
	}
	
	public Map<String, Integer> getTopTen() throws SQLException { //get the orderitems to get the top ten books sold
		String query = ("SELECT * FROM ORDERITEMS");
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		Map<String, Integer> orderItems= new HashMap<String, Integer>();
		while (r.next()) {
			String key = r.getString("bid") + ", " + r.getString("name");
			int quantity = r.getInt("quantity");
			if (orderItems.containsKey(key)) {
				orderItems.put(key, orderItems.get(key) + quantity);
			}
			else {
				orderItems.put(key, quantity);
			}
			
		}
		r.close();
		p.close();
		con.close();
		return orderItems;
	}
	
	public Map<String,OrderBean> getOrderItemsByBID(String bid) throws SQLException { //Get orders by bid
		String query = "Select * from orderitems where bid='" + bid +"'";
		
		Connection con = (this.ds).getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		OrderBean order=null;
		Map<String,OrderBean> orders2 = null;
		ArrayList<OrderBean> ordersforBID= new ArrayList<OrderBean>();
		while (r.next()) {
			order = new OrderBean(r.getString("id"), r.getString("bid"), r.getString("name"), r.getInt("price"), r.getInt("quantity"));
			ordersforBID.add(order);
		}
		r.close();
		p.close();
		con.close();
		
		orders2= new HashMap<String,OrderBean>();
		for (int i = 0; i < ordersforBID.size(); i ++) {
			String query2 = "Select * from orders where id='" + ordersforBID.get(i).getId() +"'";
			Connection con2 = (this.ds).getConnection();
			PreparedStatement p2 = con2.prepareStatement(query2);
			ResultSet r2 = p2.executeQuery();
			//OrderBean order2=null;
			
			while (r2.next()) {
				ordersforBID.get(i).setStreet(r2.getString("street"));
				ordersforBID.get(i).setCountry(r2.getString("country"));
				ordersforBID.get(i).setProvince(r2.getString("province"));
				ordersforBID.get(i).setZip(r2.getString("zip"));
				ordersforBID.get(i).setStreetBill(r2.getString("billingstreet"));
				ordersforBID.get(i).setCountryBill(r2.getString("billingcountry"));
				ordersforBID.get(i).setProvinceBill(r2.getString("billingprovince"));
				ordersforBID.get(i).setZipBill(r2.getString("billingzip"));
				ordersforBID.get(i).setUsername(r2.getString("username"));
				ordersforBID.get(i).setFirstname(r2.getString("firstname"));
				ordersforBID.get(i).setLastname(r2.getString("lastname"));
				ordersforBID.get(i).setDate(r2.getString("date"));
			}
			r2.close();
			p2.close();
			con2.close();
			
			orders2.put(ordersforBID.get(i).getId(),ordersforBID.get(i));
		}
		
	
		
		
		return orders2;
	}
}
