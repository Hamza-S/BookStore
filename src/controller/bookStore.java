package controller;

import model.Books;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;

/**
 * Servlet implementation class bookStore
 */
@WebServlet("/bookStore")
public class bookStore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public bookStore() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext svcnxt = getServletContext();
		try {
			svcnxt.setAttribute("model", Books.getInstance());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Books book = (Books) request.getServletContext().getAttribute("model");
		// TODO Auto-generated method stub
		try {

			Map<String, BookBean> rv = book.getLibrary();
			String books = book.generateBookCards(rv);
			request.getServletContext().setAttribute("library", books);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getParameter("search") != null && request.getParameter("search").equals("true")) {
			
			String title = request.getParameter("bookTitle");
			try {

				Map<String, BookBean> rv = book.searchLibrary(title);
				String searchRes = book.generateBookCards(rv);
				request.setAttribute("searchResults", searchRes);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/searchresults.jspx").forward(request, response);
		} else {
			request.getRequestDispatcher("/home.jspx").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
