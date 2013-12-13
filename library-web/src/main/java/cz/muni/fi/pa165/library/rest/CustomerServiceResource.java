package cz.muni.fi.pa165.library.rest;

import cz.muni.fi.pa165.library.service.CustomerService;
import cz.muni.fi.pa165.library.to.CustomerTo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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

    final static Logger log = LoggerFactory.getLogger(CustomerServiceResource.class);

    @Context
    private UriInfo context;

    @Autowired
    protected CustomerService customerService;

    @Path("{id}")
    public CustomerResource getCustomerResource(@PathParam("id") Integer id) {
	log.debug("customers: find by id " + id);
	CustomerTo customerTo = null;
	try {
	    customerTo = customerService.findCustomerById(id.longValue());
	} catch (Exception ex) {
	    log.error("getCustomerResource by id exception", ex);
	    throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
	}
	return ResourceConvertor.fromCustomerTo(customerTo);
    }

    @GET
    @Path("json/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerResource> getJsonAll() {
	List<CustomerTo> customers;
	try {
	    customers = customerService.findAllCustomers();
	} catch (Exception ex) {
	    log.error("getJsonAll", ex);
	    throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
	}
	if (customers == null) {
	    return null;
	}
	List<CustomerResource> resourceCustomers = new ArrayList<>();
	for (CustomerTo customerTo : customers) {
	    resourceCustomers.add(ResourceConvertor.fromCustomerTo(customerTo));
	}
	return resourceCustomers;
    }

    @GET
    @Path("json/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerResource getJsonId(@PathParam("id") Integer id) {
	CustomerTo customerTo = null;
	try {
	    customerTo = customerService.findCustomerById(id.longValue());
	} catch (Exception ex) {
	    log.error("getJsonId", ex);
	    throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
	}
	return ResourceConvertor.fromCustomerTo(customerTo);
    }

    @GET
    @Path("json/fullname/{firstName}/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerResource> getJsonFullName(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName) {
	List<CustomerResource> resourceCustomers = new ArrayList<>();
	if (firstName == null && lastName == null) {
	    throw new WebApplicationException(Response.Status.BAD_REQUEST);
	}
	List<CustomerTo> customers;
	try {
	    customers = customerService.findCustomerByName(firstName, lastName);
	} catch (Exception ex) {
	    log.error("getJsonFullName", ex);
	    throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
	}
	if (customers == null) {
	    return null;
	}
	for (CustomerTo customerTo : customers) {
	    resourceCustomers.add(ResourceConvertor.fromCustomerTo(customerTo));
	}
	return resourceCustomers;
    }

    @GET
    @Path("json/lastName/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerResource> getJsonSurname(@PathParam("lastName") String lastName) {
	return this.getJsonFullName(null, lastName);
    }

    @GET
    @Path("json/firstname/{firstName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerResource> getJsonName(@PathParam("firstName") String firstName) {
	return this.getJsonFullName(firstName, null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json")
    public Response postJson(CustomerResource customerResource) {
	CustomerTo customerTo;
	try {
	    customerTo = customerService.addCustomer(ResourceConvertor.fromCustomerResource(customerResource));
	} catch (Exception ex) {
	    log.debug("Create customer - server error" + ex);
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	log.debug("Created customer " + customerTo.getId());
	return Response.created(URI.create(context.getAbsolutePath() + "/" + customerTo.getId())).entity(customerTo).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json/put/{id}")
    public Response putJson(@PathParam("id") Integer id, CustomerResource customerResource) {
	log.debug("----  putting item ");
	Response response;
	Long idLong = id.longValue();
	try {
	    CustomerTo customer = customerService.findCustomerById(idLong);
	    if (customer == null) {
		return Response.status(Response.Status.NOT_FOUND).build();
	    } else {
		customerResource.setId(idLong);
		customerService.updateCustomer(ResourceConvertor.fromCustomerResource(customerResource));
		response = Response.noContent().build();
	    }
	} catch (Exception ex) {
	    log.error("customers rest: putJson - ", ex);
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	return response;
    }

    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
	log.info("---- Deleting item nr. " + id);
	CustomerTo customer;
	try {
	    customer = customerService.findCustomerById(id.longValue());
	    if (customer == null) {
		return Response.status(Response.Status.NOT_FOUND).build();
	    }
	    if (customerService.deleteCustomer(customer)) {
		return Response.status(Response.Status.OK).build();
	    } else {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	    }
	} catch (Exception ex) {
	    log.error("customers rest: delete - ", ex);
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
    }
}
