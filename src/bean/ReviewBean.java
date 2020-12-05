package bean;

public class ReviewBean {
	private String bid;
	private String username;
	private String title;
	private String review;
	private int rating;
	
	public ReviewBean() {
		
	}
	
public ReviewBean(String username, String bid, String title, String review, int rating) {
	this.bid = bid;
	this.username = username;
	this.title = title;
	this.review = review;
	this.rating = rating;
	}

public String getBid() {
	return bid;
}

public void setBid(String bid) {
	this.bid = bid;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getReview() {
	return review;
}

public void setReview(String review) {
	this.review = review;
}

public int getRating() {
	return rating;
}

public void setRating(int rating) {
	this.rating = rating;
}
	
public String toString() { 
    
	return "bid: '" + this.bid + "', username: '" + this.username + "', title: '" 
	+ this.title + "'"+ "', review: '" + this.review + "'"
	+ "', rating: '" + this.rating + "'"
	;
} 

}
