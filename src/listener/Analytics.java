package listener;

import javax.servlet.annotation.WebListener;
import dao.OrdersDAO;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class Analytics implements HttpSessionAttributeListener {
	
	double maxP = 0;
    /**
     * Default constructor. 
     * @throws ClassNotFoundException 
     */
    public Analytics() throws ClassNotFoundException {
        // TODO Auto-generated constructor stub
    	OrdersDAO oDAO = new OrdersDAO();
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	handleEvent(arg0);
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	handleEvent(arg0);
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	handleEvent(arg0);
    }
	
    void handleEvent(HttpSessionBindingEvent arg0) {
    	if (arg0.getName().equals("principal"))
    	{
    		/*
    		 * if the attribute firstName is changed then we record it
    		 * we save the counter name in a context attribute..
    		 */
    				
    				double temp = (double) arg0.getSession().getAttribute("principal");
    				if (temp >= maxP) {
    					maxP = temp;
    					arg0.getSession().getServletContext().setAttribute("maxPrincipal", maxP);
    				}
    			 				
    			
    	
    }
    }
}
