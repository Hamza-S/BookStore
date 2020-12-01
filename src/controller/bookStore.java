package controller;

import model.Books;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			Map<String, BookBean> rv = Books.getInstance().getLibrary();
			String books = Books.getInstance().generateBookCards(rv);
			request.getServletContext().setAttribute("library", books);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getParameter("search") != null && request.getParameter("search").equals("true")) {
			
			String title = request.getParameter("bookTitle");
			try {

				Map<String, BookBean> rv = Books.getInstance().searchLibrary(title);
				String searchRes = Books.getInstance().generateBookCards(rv);
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
