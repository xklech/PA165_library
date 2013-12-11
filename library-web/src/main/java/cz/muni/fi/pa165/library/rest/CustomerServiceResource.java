package cz.muni.fi.pa165.library.rest;

import cz.muni.fi.pa165.library.BooksActionBean;
import cz.muni.fi.pa165.library.service.CustomerService;
import cz.muni.fi.pa165.library.to.CustomerTo;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Path("/customers")
@Component
public class CustomerServiceResource {
    
    final static Logger log = LoggerFactory.getLogger(BooksActionBean.class);
    
    @Context
    private UriInfo context;
    
    @Autowired
    protected CustomerService customerService; 
    
    @GET
    @Produces("text/plain")
    public String getText() {
        return "hello!";
    }
    
    @Path("{id}")
    public CustomerResource getCustomerResource(@PathParam("id") Integer id) {
        log.debug("customers: find by id "+id);
        return ResourceConvertor.fromCustomerTo(customerService.findCustomerById(id.longValue()));
    }
    
    @GET
    @Path("json/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerResource getJson(@PathParam("id") Integer id) {
        return ResourceConvertor.fromCustomerTo(customerService.findCustomerById(id.longValue()));
    }
    
     
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json/add")
    public Response postJson(CustomerResource customerResource) {
        customerService.addCustomer(ResourceConvertor.fromCustomerResource(customerResource));
        log.debug("Created customer " + customerResource.getId());
        return Response.created(URI.create(context.getAbsolutePath() + "/"+ customerResource.getId())).build();
    }
    
}
