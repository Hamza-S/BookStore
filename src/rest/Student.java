package rest;
import javax.ws.rs.Consumes;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import model.Books;
import model.SIS;

@Path("partners")
public class Student {
	
	@GET
    @Path("/getProductInfo")
	@Produces("text/plain")
	public String getInfo(@QueryParam("bid") String bid) throws Exception{
		String xml = Books.getInstance().export(name, 0);
		System.out.println(xml);
		return xml;	
		
	}
	@GET
    @Path("/getOrdersByBid")
	@Produces("text/plain")
	public String insertStudent(@QueryParam("bid") String bid) throws Exception{
		int success = Books.getInstance().insertStudent(sid, givenName, surName, creditTaken, creditGraduate);
		System.out.println(success);
		return ("InsertedRows: " + success);	
		
	}

 

}
