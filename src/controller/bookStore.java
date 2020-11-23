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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			String books = "";
			Map<String, BookBean> rv = Books.getInstance().getLibrary();
			BookBean b = null;
			for (Map.Entry<String, BookBean> i : rv.entrySet()) {
				b = i.getValue();
				books += "<div class=\"col-md-4\">";
				books += "<div class=\"card mb-4 shadow-sm\">";
				books += "<svg class=\"bd-placeholder-img card-img-top\" width=\"100%\" height=\"225\" xmlns=\"http://www.w3.org/2000/svg\"\r\n" + 
						"preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\">\r\n" + 
						"<image href=\"https://upload.wikimedia.org/wikipedia/en/c/c9/Harry_Potter_and_the_Goblet_of_Fire_Poster.jpg\"></image>\r\n" + 
						"</svg>";
				books += "<div class=\"card-body\">";
				books += "<h6 class=\"card-text\"> " + b.getTitle() + "</h6>";
				books += "<h7 class=\"card-text\"> " + b.getCategory() + "</h7>";
				books += "<br />";
				books += "<h7 class=\"card-text\"> $" + b.getPrice() + "</h7>";
				books += "<br />";
				books += "<div class=\"btn-group\">";
				books += "<form action=\"bookStore\" method=\"GET\">";
				books += "<input type=\"hidden\" name=\"bid\" value=\"" + b.getBid() + "\" />";
				books += "<button action='submit' class=\"btn btn-sm btn-outline-secondary\" name='moreInfo' value=\"true\">More Info</button>";
				books += "</form>";
				books += "</div>";
				books += "</div>";
				books += "</div>";
				books += "</div>";

			}
			request.getServletContext().setAttribute("library", books);
		}catch(Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/home.jspx").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
