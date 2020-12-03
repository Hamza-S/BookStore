package bean;

public class UserBean {

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private boolean isLoggedIn = false;
	private CartBean cart;

	public UserBean() {
		this.cart = new CartBean();
	}

	public UserBean(String fn, String ln, String un, String e, String p) {
		this.cart = new CartBean();
		this.firstName = fn;
		this.lastName = ln;
		this.userName = un;
		this.email = e;
		this.password = p;
		this.isLoggedIn = true;
	}

	public CartBean getCart() {
		return cart;
	}

	public void setCart(CartBean cart) {
		this.cart = cart;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
