package rest;
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

@Path("partners")
public class Partners {
	
	@GET
    @Path("/getProductInfo")
	@Produces("text/plain")
	public String getInfo(@QueryParam("bid") String bid) throws Exception{
		String json = Books.getInstance().export_json(bid);
		System.out.println(json);
		return json;	
		
	}


}
