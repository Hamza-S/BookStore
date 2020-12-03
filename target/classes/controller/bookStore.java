package controller;

import model.Books;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.BookBean;
import bean.UserBean;

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
			svcnxt.setAttribute("model", Books.getInstance()); // Singleton design pattern for model
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Books book = (Books) request.getServletContext().getAttribute("model");
		HttpSession session = request.getSession();
		if (session.getAttribute("UserBean") == null) { // Initialize a guest user with an empty cart upon initial visit/not logged in
													
			UserBean user = new UserBean();
			session.setAttribute("UserBean", user);
		} else {
			//Do nothing, userbean already generated, or the user is logged in.
		}

		String path = request.getPathInfo();
		try {

			Map<String, BookBean> rv = book.getLibrary();
			String books = book.generateBookCards(rv);
			request.setAttribute("library", books);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getParameter("search") != null && request.getParameter("search").equals("true")) { //Search functionality
			try {
				boolean registered = book.registerUser("Hamza", "Saleem", "hamzabman", "hamzabman@gmail.com",
						"testing123");
				System.out.println(registered);
				System.out.println(book.login("hamzabman", "testing123"));
			} catch (NoSuchAlgorithmException | SQLException e1) {
				e1.printStackTrace();
			}

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

		} else if (path != null) { // Page redirections based on request
			if (path.equals("/Login")) {
				request.getRequestDispatcher("/login.jspx").forward(request, response);
			} else if (path.equals("/Register")) {
				request.getRequestDispatcher("/register.jspx").forward(request, response);
			} else if (path.equals("/Cart")) {
				request.getRequestDispatcher("/cart.jspx").forward(request, response);
			}

			else {
				request.getRequestDispatcher("/home.jspx").forward(request, response);
			}

		} else if (request.getParameter("fetch") != null && request.getParameter("fetch").equals("true")) {  //Ajax handling for home page																								 
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
		} else {
			request.getRequestDispatcher("/home.jspx").forward(request, response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
