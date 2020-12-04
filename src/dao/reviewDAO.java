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
	public String generateReviewHTML(String bid) throws SQLException {
		String result="";
		ArrayList<ReviewBean> reviews=getBookReviews(bid);
//		reviews.get
		for(int i =0; i<reviews.size(); i++ ) {
			
			result+=
					"<div class=\"row\">"+
			"<div class=\"col-sm-3\">"+
			"<div class=\"review-block-name\"><span class=\"userName\">"+reviews.get(i).getUsername()+"</span></div>"+
			"</div>"+
			"<div class=\"col-sm-9\">"+
			"<div class=\"review-block-rate\">"+
			"<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"gold\" xmlns=\"http://www.w3.org/2000/svg\">"+
			"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
			"</svg>";
			if(reviews.get(i).getRating()>1) {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"gold\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			else {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			if(reviews.get(i).getRating()>2) {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"gold\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			else {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			if(reviews.get(i).getRating()>3) {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"gold\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			else {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			if(reviews.get(i).getRating()>4) {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"gold\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			else {
				result+="<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-star-fill\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">"+
						"<path d=\"M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z\"/>"+
						"</svg>";	
			}
			result+="</div>"+
			"<div class=\"review-block-title\">"+reviews.get(i).getTitle()+"</div>"+
			"<div class=\"review-block-description\">"+reviews.get(i).getReview()+"</div>"+
			"</div>"+
			"</div>"+
			"<hr/>";			
		}
		
		
		
		
		return result;
	}
		
	

}
