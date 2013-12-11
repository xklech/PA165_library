
package cz.muni.fi.pa165.library.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
 
@Path("service")
public class ServiceResource {
 
    @Context
    private UriInfo context;
  
    public ServiceResource() {
    }
 
    @GET
    @Produces("text/plain")
    public String getText() {
        return "hello!";
    }
}