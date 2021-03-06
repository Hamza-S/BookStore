package controller;

import model.Books;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bean.BookBean;
import bean.OrderBean;
import bean.ReviewBean;
import bean.UserBean;
import dao.BooksDAO;

@WebServlet({ "/bookStore", "/bookStore/*" })
public class bookStore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public bookStore() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext svcnxt = getServletContext();
		try {
			svcnxt.setAttribute("placedOrderCount", 0);
			svcnxt.setAttribute("model", Books.getInstance()); // Singleton design pattern for model
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		svcnxt.setAttribute("placedOrderCount", 0);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Books book = (Books) request.getServletContext().getAttribute("model");
		HttpSession session = request.getSession();
		if (session.getAttribute("UserBean") == null) { // Initialize a guest user with an empty cart upon initial
														// visit/not logged in

			UserBean user = new UserBean();
			session.setAttribute("UserBean", user);
			session.setAttribute("orderRequestCount", Integer.parseInt("0"));

		} else {
			UserBean user = (UserBean) session.getAttribute("UserBean");

			// Do nothing, userbean already generated, or the user is logged in.
		}

		String path = request.getPathInfo();
		try {

			Map<String, BookBean> rv = book.getLibrary();
			String books = book.generateBookCards(rv);
			request.setAttribute("library", books);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getParameter("search") != null && request.getParameter("search").equals("true")) { // Search for a book
			String title = request.getParameter("bookTitle");
			try {

				Map<String, BookBean> rv = book.searchLibrary(title);
				String searchRes = book.generateBookCards(rv);
				request.setAttribute("searchResults", searchRes);
				request.setAttribute("resultCount", rv.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/searchresults.jspx").forward(request, response);
		}
		// Add a review if user is logged in
		else if (request.getParameter("reviewAdded") != null && request.getParameter("reviewAdded").equals("true")) { //Adding review to a book
			boolean loggedIn = false;

			if (request.getSession().getAttribute("isLoggedIn") != null) {
				loggedIn = (boolean) request.getSession().getAttribute("isLoggedIn");
			}

			if (loggedIn) { //check if logged in
				String userName = (String) request.getSession().getAttribute("userName");
				String reviewTitle = request.getParameter("reviewTitle");
				String bid = request.getParameter("bid");
				String newReview = request.getParameter("newReview");
				String rating = request.getParameter("rating");
				try {
					int success = Books.getInstance().addReview(userName, bid, reviewTitle, newReview, rating);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
			}
			//forward back to home page			
			request.getRequestDispatcher("home.jspx").forward(request, response);

		}

		else if (request.getParameter("addtoCart") != null && request.getParameter("addtoCart").equals("true")) { //Adding item to the cart
			UserBean s = (UserBean) request.getSession().getAttribute("UserBean");
			String bid = request.getParameter("bookid");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			s.getCart().addItem(bid, quantity);
			request.getSession().setAttribute("UserBean", s);
			request.getSession().setAttribute("CartNum", s.getCart().getTotalQuantity());
			request.getRequestDispatcher("/home.jspx").forward(request, response);

		}
		// Add a review if user is logged in
		else if (request.getParameter("moreInfo") != null && request.getParameter("moreInfo").equals("true")) {
			String bid = request.getParameter("bid");
			try {
				request.setAttribute("bid", book.getBook(bid).getBid());
				request.setAttribute("category", book.getBook(bid).getCategory());
				request.setAttribute("price", book.getBook(bid).getPrice());
				request.setAttribute("title", book.getBook(bid).getTitle());
				String bookReviews = book.generateReviewHTML(bid);
				request.setAttribute("bookReviews", bookReviews);
				Map<String, Long> stats = book.getReviewStats(bid);

				// Stats Retrieved and Formated:
				long size = stats.get("size");
				long percent1 = stats.get("percent1");
				long percent2 = stats.get("percent2");
				long percent3 = stats.get("percent3");
				long percent4 = stats.get("percent4");
				long percent5 = stats.get("percent5");

				double avgRating = (1 * ((double) percent1 / 100) + 2 * ((double) percent2 / 100)
						+ 3 * ((double) percent3 / 100) + 4 * ((double) percent4 / 100)
						+ 5 * ((double) percent5 / 100));
				double roundedAvgRating = avgRating * 10;
				roundedAvgRating = Math.round(roundedAvgRating);
				roundedAvgRating = roundedAvgRating / 10;

				// Save Stats in request session
				request.setAttribute("percent1", percent1);
				request.setAttribute("percent2", percent2);
				request.setAttribute("percent3", percent3);
				request.setAttribute("percent4", percent4);
				request.setAttribute("percent5", percent5);
				request.setAttribute("numOfReviews", size);
				request.setAttribute("avgRating", roundedAvgRating);

				// Set Showing the Review button
				String userName = (String) request.getSession().getAttribute("userName");
				boolean showAddRev = false;

				if (request.getSession().getAttribute("isLoggedIn") != null) {

					if (((boolean) request.getSession().getAttribute("isLoggedIn")) == true) {
						if (!book.userReviewedTheBook(userName, bid)) {
							showAddRev = true;
						}
					}
				}

				request.setAttribute("showAddReview", showAddRev);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("/bookinfo.jspx").forward(request, response);

		}

		else if (request.getParameter("login") != null && request.getParameter("login").equals("true")) { //Login user
			//Get params
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			request.getSession().removeAttribute("loginError");
			request.getSession().removeAttribute("registerError");
			try {
				if (book.login(username, password)) {
					// If they login, we want to save their cart
					UserBean usr = (UserBean) request.getSession().getAttribute("UserBean");
					Map<String, Integer> usrCart = usr.getCart().getCart(); //Save their cart
					UserBean user = book.getUserBean(username);
					user.getCart().setCart(usrCart);
					request.getSession().setAttribute("UserBean", user); //Update the user after
					//Get their address values
					String street = book.getAddressAttribute(username, "street");
					String country = book.getAddressAttribute(username, "country");
					String province = book.getAddressAttribute(username, "province");
					String zip = book.getAddressAttribute(username, "zip");
					request.getSession().setAttribute("street", street);
					request.getSession().setAttribute("country", country);
					request.getSession().setAttribute("province", province);
					request.getSession().setAttribute("zip", zip);
					request.getSession().setAttribute("isLoggedIn", true);
					request.getSession().setAttribute("userName", user.getUserName());// delete when log out
					if (request.getParameter("fromPayment") != null) {
						request.getRequestDispatcher("/payment.jspx").forward(request, response);
					} else {
						request.getRequestDispatcher("/home.jspx").forward(request, response);
					}
				} else {
					// throw error incorrect password/authentication failed
					request.getSession().setAttribute("loginError",
							"Incorrect password or the username does not exist!");
					if (request.getParameter("fromPayment") != null) {
						request.getRequestDispatcher("/payment.jspx").forward(request, response);
					} else {
						request.getRequestDispatcher("/login.jspx").forward(request, response);
					}
				}
			} catch (NoSuchAlgorithmException | SQLException e) {
				e.printStackTrace();
			}

		}  else if (request.getParameter("register") != null && request.getParameter("register").equals("true")) { //Register user
			//Get needed request/form params
			String fName = request.getParameter("fname");
			String lName = request.getParameter("lname");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");// handler
			String password2 = request.getParameter("password2");
			String address = request.getParameter("address");
			String country = request.getParameter("country");
			String province = request.getParameter("province");
			String zip = request.getParameter("zip");
			request.getSession().removeAttribute("loginError");
			request.getSession().removeAttribute("registerError");
			try {
				if (book.registerUser(fName, lName, username, email, password) && (password.contentEquals(password2))) { //Register the user
					book.insertAddress(username, address, province, country, zip);
					request.getRequestDispatcher("/login.jspx").forward(request, response);
				} else {
					// throw error incorrect password/authentication failed
					if (!(password.contentEquals(password2))) {//Error checking
						request.getSession().setAttribute("registerError", "Error, both passwords must match!");
					} else {
						request.getSession().setAttribute("registerError", "Error, username already exists!");
					}

					if (request.getParameter("fromPayment") != null) {
						request.getRequestDispatcher("/payment.jspx").forward(request, response);
					} else {
						request.getRequestDispatcher("/register.jspx").forward(request, response);
					}

				}
			} catch (NoSuchAlgorithmException | SQLException e) {
				e.printStackTrace();
			}

		}

		else if (request.getParameter("logout") != null && request.getParameter("logout").equals("true")) { //Logout user
			// button
			UserBean loggedoutUser = new UserBean();
			UserBean currUser = (UserBean) request.getSession().getAttribute("UserBean");
			loggedoutUser.getCart().setCart(currUser.getCart().getCart());
			request.getSession().setAttribute("UserBean", loggedoutUser);
			request.getSession().removeAttribute("isLoggedIn");
			request.getSession().removeAttribute("userName");
			request.getRequestDispatcher("/home.jspx").forward(request, response);

		} else if (request.getParameter("checkout") != null && request.getParameter("checkout").equals("true")) { //Redirect to payment page from cart
			// button
			UserBean currUser = (UserBean) request.getSession().getAttribute("UserBean");
			String genCheckoutCart = "";
			try {
				genCheckoutCart = currUser.getCart().generateCheckoutCartHTML();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().setAttribute("genCheckoutHTML", genCheckoutCart);
			request.getRequestDispatcher("payment.jspx").forward(request, response);

		} else if (request.getParameter("PlaceOrder") != null && request.getParameter("PlaceOrder").equals("true")) { //Method to submit order after payment
			
			//Get needed parameters from request
			String street = request.getParameter("street");
			String province = request.getParameter("province");
			String country = request.getParameter("country");
			String zip = request.getParameter("zip");
			String billingstreet = request.getParameter("billingstreet");
			String billingprovince = request.getParameter("billingprovince");
			String billingcountry = request.getParameter("billingcountry");
			String billingzip = request.getParameter("billingzip");
			LocalDate date = java.time.LocalDate.now();

			UserBean user = (UserBean) request.getSession().getAttribute("UserBean");
			int currentCount = (int) session.getAttribute("orderRequestCount");
			user.setOrderRequestCounter(currentCount + 1);
			session.setAttribute("orderRequestCount", user.getOrderRequestCounter());
			Map<String, Integer> cart = user.getCart().getCart();
			SecureRandom rand = new SecureRandom();
			String id = UUID.randomUUID().toString(); //Generate random order id
			String bid = "";
			String title = "";
			int price = 0;
			int quantity = 0;
			int success = 0;
			BookBean bookb = null;
			if (user.getOrderRequestCounter() % 3 != 0) { //checking for every 3rd request
				try {
					success = book.InsertOrder(id, street, province, country, zip, billingstreet, billingprovince,
							billingcountry, billingzip, user.getUserName(), user.getFirstName(), user.getLastName(),
							date.toString());

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				for (Map.Entry<String, Integer> entry : cart.entrySet()) {
					bid = entry.getKey();
					quantity = entry.getValue();
					try {
						bookb = book.getBook(bid);
						title = bookb.getTitle();
						book.InsertOrderItem(id, bid, title, bookb.getPrice(), quantity);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				user.getCart().clearCart(); //empty the cart after they place their order
				if (success == 1) {
					int oldCount = (int) request.getServletContext().getAttribute("placedOrderCount");
					request.getServletContext().setAttribute("placedOrderCount", oldCount + 1);
				}
				
				//Store needed values into session
				request.getSession().setAttribute("CartNum", user.getCart().getTotalQuantity());
				request.getSession().setAttribute("UserBean", user);
				request.getRequestDispatcher("/receipt.jspx").forward(request, response);
			}
			// order request count = 0, meaning it is an every 3rd payment request
			else {
				request.getSession().setAttribute("CartNum", user.getCart().getTotalQuantity());
				request.getSession().setAttribute("UserBean", user);
				request.getRequestDispatcher("/failedPayment.jspx").forward(request, response);
			}

		} else if (request.getParameter("updateCart") != null && request.getParameter("updateCart").equals("true")) { //Method to update cart
																														 
			UserBean user = (UserBean) request.getSession().getAttribute("UserBean");
			int itemsinCart = user.getCart().getCart().size();
			String genCart = "";
			String quantityParam = "";
			String bidParam = "";
			int paramNum = 0;

			for (int i = 0; i < itemsinCart; i++) {
				paramNum = i + 1;
				bidParam = "bid" + paramNum + "quant";
				quantityParam = "item" + paramNum + "quant";

				bidParam = request.getParameter(bidParam);
				quantityParam = request.getParameter(quantityParam);

				user.getCart().updateQuantity(bidParam, Integer.parseInt(quantityParam));
			}
			request.getSession().setAttribute("CartNum", user.getCart().getTotalQuantity());
			request.getSession().setAttribute("UserBean", user);

			try {

				genCart = user.getCart().generateCartHTML();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.getSession().setAttribute("genCartHTML", genCart);

			request.getRequestDispatcher("cart.jspx").forward(request, response);

		} else if (request.getParameter("ordersbyMonth") != null //Get orders by month
				&& request.getParameter("ordersbyMonth").equals("true")) {
			ArrayList<OrderBean> orders = null;
			String month = request.getParameter("month");
			try {
				orders = book.getOrdersByMonth(month);
				String monthName = book.getMonth(month);
				OrderBean order = null;
				String output = "<h3>" + monthName + " Report</h3><br/>";
				output += "<table class=\"table\" id=\"analyticsTable\">";
				output += "<thead class=\"thead-dark\"><tr><th scope=\"col\">Order ID</th><th scope=\"col\">Book ID</th><th scope=\"col\">Title</th><th scope=\"col\">Price</th><th scope=\"col\">Quantity</th></tr></thead><tbody>";
				for (int i = 0; i < orders.size(); i++) {
					order = orders.get(i);
					output += "<tr><td>" + order.getId() + "</td>" + "<td>" + order.getBid() + "</td>" + "<td>"
							+ order.getTitle() + "</td>" + "<td>" + order.getPrice() + "</td>" + "<td>"
							+ order.getQuantity() + "</td></tr>";

				}
				output += "</tbody></table>";
				request.getServletContext().setAttribute("adminAnalytics", output);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/admin.jspx").forward(request, response);

		} else if (request.getParameter("mostPopular") != null && request.getParameter("mostPopular").equals("true")) { //Get top 10 books
			Map<String, Integer> topTen = null;

			ServletContext svcnxt = getServletContext();

			topTen = (Map<String, Integer>) svcnxt.getAttribute("topTenOrders");
			String titleReport = "Most Popular Books";
			String output = "<h3>" + titleReport + " Report</h3><br/>";
			output += "<table class=\"table\" id=\"analyticsTable\">";
			output += "<thead class=\"thead-dark\"><tr><th scope=\"col\">Rank</th><th scope=\"col\">Book ID</th><th scope=\"col\">Title</th><th scope=\"col\">Copies Sold</th></tr></thead><tbody>";
			int rank = 1;
			for (Map.Entry<String, Integer> entry : topTen.entrySet()) {
				String split[] = entry.getKey().split(", ");
				String bid = split[0];
				String title = split[1];
				int quantity = entry.getValue();
				output += "<tr><td>" + rank + "</td>" + "<td>" + bid + "</td>" + "<td>" + title + "<td>" + quantity
						+ "</td></tr>";
				rank++;

			}
			output += "</tbody></table>";
			request.getServletContext().setAttribute("adminAnalytics", output);
			request.getRequestDispatcher("/admin.jspx").forward(request, response);

		}
		// =========================PAGE REDIRECTIONS BASED ON REQUESTED
		// PATH========================================
		else if (path != null) {
			if (path.equals("/Login")) {
				request.getRequestDispatcher("/login.jspx").forward(request, response);
			} else if (path.equals("/Register")) {
				request.getRequestDispatcher("/register.jspx").forward(request, response);
			} else if (path.equals("/Cart")) {
				UserBean currUser = (UserBean) request.getSession().getAttribute("UserBean");
				String genCart = "";
				try {
					genCart = currUser.getCart().generateCartHTML();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("genCartHTML", genCart);
				request.getRequestDispatcher("/cart.jspx").forward(request, response);

			} else if (path.equals("/Payment")) {
				UserBean currUser = (UserBean) request.getSession().getAttribute("UserBean");
				String genCheckoutCart = "";
				try {
					genCheckoutCart = currUser.getCart().generateCheckoutCartHTML();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getSession().setAttribute("genCheckoutHTML", "hi mom");
				request.getRequestDispatcher("/payment.jspx").forward(request, response);

			} else if (path.contentEquals("/404")) {
				request.setAttribute("errorMessage", "404 ERROR PAGE NOT FOUND!");
				request.getRequestDispatcher("/404.jspx").forward(request, response);
			} else if (path.contentEquals("/403")) {
				request.setAttribute("errorMessage", "403 ERROR ACCESS DENIED!");
				request.getRequestDispatcher("/404.jspx").forward(request, response);
			} else if (path.equals("/Admin")) {

				String username = (String) request.getSession().getAttribute("userName");
				if (username != null) {
					if (!username.contentEquals("admin")) {
						request.setAttribute("errorMessage", "403 ERROR ACCESS DENIED!");
						response.sendRedirect(request.getContextPath() + "/bookStore/403");
					} else if (username.contentEquals("admin")) {
						request.getRequestDispatcher("/admin.jspx").forward(request, response);
					}
				} else {
					request.setAttribute("errorMessage", "403 ERROR ACCESS DENIED!");
					response.sendRedirect(request.getContextPath() + "/bookStore/403");
				}

			} else if (path.equals("/Info")) {
				request.getRequestDispatcher("/bookinfo.jspx").forward(request, response);
			} else {
				request.setAttribute("errorMessage", "404 ERROR PAGE NOT FOUND!");
				response.sendRedirect(request.getContextPath() + "/bookStore/404");
			}

		} else if (request.getParameter("fetch") != null && request.getParameter("fetch").equals("true")) {
			String category = request.getParameter("category");
			try {

				Map<String, BookBean> rv = book.getBooksByCategory(category);
				String searchRes = book.generateBookCards(rv);
				request.setAttribute("library", searchRes);
				request.setAttribute("resultCount", rv.size());

				response.setContentType("text/html");
				response.getWriter().append(searchRes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else {

			request.getRequestDispatcher("/home.jspx").forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
