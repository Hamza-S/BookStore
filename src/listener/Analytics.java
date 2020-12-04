package listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import bean.OrderBean;
import dao.OrdersDAO;

/**
 * Application Lifecycle Listener implementation class Analytics
 *
 */
@WebListener
public class Analytics implements ServletContextAttributeListener {
	int orderCount = 0;

	/**
	 * Default constructor.
	 */
	public Analytics() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent event) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent event) {
		try {
			handleEvent(event);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent event) {
		try {
			handleEvent(event);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}

	void handleEvent(ServletContextAttributeEvent event) throws ClassNotFoundException, SQLException {
		if (event.getName().equals("placedOrderCount")) {

			int orderCount = (int) event.getServletContext().getAttribute("placedOrderCount");
			OrdersDAO oDAO = new OrdersDAO();
			Map<String, Integer> allOrders = oDAO.getTopTen();
			List<Map.Entry<String, Integer>> sortedOrders = new LinkedList<Map.Entry<String, Integer>>(
					allOrders.entrySet());
			Collections.sort(sortedOrders, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			});
			Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>(); //Sort the map
			for (Map.Entry<String, Integer> entry : sortedOrders) {
				sortedMap.put(entry.getKey(), entry.getValue());
			}
			int i = 0; 
			Map<String, Integer> topTen = new LinkedHashMap<String, Integer>();
			for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
				//System.out.println("#" + i + " " + entry.getKey() +" sold " + entry.getValue() + " copies"); //Generate a top ten map
				topTen.put(entry.getKey(), entry.getValue());
				i++;
				if (i == 10) {
					break;
				}
			}
			
			for (Map.Entry<String, Integer> entry : topTen.entrySet()) {
				System.out.println("#" + i + " " + entry.getKey() +" sold " + entry.getValue() + " copies"); //Generate a top ten map
			
			}
			event.getServletContext().setAttribute("topTenOrders", topTen);

		}
	}
}
