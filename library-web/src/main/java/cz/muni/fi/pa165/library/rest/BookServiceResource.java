package cz.muni.fi.pa165.library.rest;

import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.to.BookTo;
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

/**
 *
 * @author Mask
 */
@Path("/books")
@Component
public class BookServiceResource {
    
    final static Logger log = LoggerFactory.getLogger(BookServiceResource.class);

    @Context
    private UriInfo context;
    
    @Autowired
    protected BookService bookService; 
    
    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
        log.debug("books: delete #" + id);
        try {
            BookTo book = bookService.findBookById(id.longValue());
            if (book == null) {
                 return Response.status(Response.Status.NOT_FOUND).build();
            }
            if (bookService.deleteBook(book)) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception ex) {
            log.error("deleting book exception", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
        
    @Path("{id}")
    public BookResource getBookResource(@PathParam("id") Integer id) {
        log.debug("books: find by id "+id);
        BookTo bookTo = null;
        try {
            bookTo = bookService.findBookById(id.longValue());
        } catch (Exception ex) {
            log.error("getBookResource by id exception",ex);
            throw new WebApplicationException(ex,Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ResourceConvertor.fromBookTo(bookTo);
    }
    
    @GET
    @Path("json/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResource getJsonId(@PathParam("id") Integer id) {
        log.debug("books: find by id json "+id);
        BookTo bookTo = null;
        try {
            bookTo = bookService.findBookById(id.longValue());
        } catch(Exception ex) {
            log.error("getBookResource by id exception",ex);
            throw new WebApplicationException(ex,Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ResourceConvertor.fromBookTo(bookTo);
    }
    

    @GET
    @Path("json/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookResource> getJsonName(@PathParam("name") String name) {
        List<BookResource> resourceBooks = new ArrayList<>();
        List<BookTo> books;
        try {        
            books = bookService.findBooksByName(name);
        } catch (Exception ex) {
            log.error("getJsonName",ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (books == null) {
            return null;
        }
        for (BookTo bookTo: books) {
            resourceBooks.add(ResourceConvertor.fromBookTo(bookTo));
        }
        return resourceBooks;
    }

    @GET
    @Path("json/author/{author}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookResource> getJsonAuthor(@PathParam("author") String author) {
        List<BookResource> resourceBooks = new ArrayList<>();
        List<BookTo> books;
        try {        
            books = bookService.findBooksByAuthor(author);
        } catch (Exception ex) {
            log.error("getJsonAuthor",ex);
            throw new WebApplicationException(ex,Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (books == null) {
            return null;
        }
        for (BookTo bookTo: books) {
            resourceBooks.add(ResourceConvertor.fromBookTo(bookTo));
        }
        return resourceBooks;
    }

    @GET
    @Path("json/department/{department}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookResource> getJsonDepartment(@PathParam("department") String department) {
        List<BookResource> resourceBooks = new ArrayList<>();
        List<BookTo> books;
        try {        
            books = bookService.findBooksByDepartment(department);
        } catch (Exception ex) {
            log.error("getJsonDepartment",ex);
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        if(books == null){
            return null;
        }
        for(BookTo bookTo: books){
            resourceBooks.add(ResourceConvertor.fromBookTo(bookTo));
        }
        return resourceBooks;
    }
    
    @GET
    @Path("json/isbn/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResource getJsonIsbn(@PathParam("isbn") String isbn) {
        BookTo book = null;
        try {        
            book = bookService.findBookByISBN(isbn);
        }catch(Exception ex){
            log.error("getJsonIsbn",ex);
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ResourceConvertor.fromBookTo(book);
    }
    
    
    @GET
    @Path("json/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookResource> getJsonAll() {
        List<BookTo> books;
        try {        
            books = bookService.findAllBooks();
        } catch (Exception ex) {
            log.error("getJsonAll",ex);
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (books == null) {
            return null;
        }
        List<BookResource> resourceBooks = new ArrayList<>();
        for(BookTo customerTo: books){
            resourceBooks.add(ResourceConvertor.fromBookTo(customerTo));
        }
        return resourceBooks;
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json")
    public Response postJson(BookResource bookRes) {
        BookTo bookTo;
        try {
            bookTo = bookService.save(ResourceConvertor.fromBookResource(bookRes));
        } catch (Exception ex) {
            log.debug("Create book - server error" , ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.debug("Created book " + bookTo.getId());
        return Response.created(URI.create(context.getAbsolutePath() + "/"+ bookTo.getId())).build();
    }
    
    @PUT
    @Path("json/put/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(@PathParam("id") Integer id, BookResource bookRes) {
        log.debug("books: update #" + bookRes.getId());
        Response response;
        Long idLong = id.longValue();
        try{
            BookTo book = bookService.findBookById(idLong);
            if (book == null) {
                response = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                bookRes.setId(idLong);
                bookService.updateBook(ResourceConvertor.fromBookResource(bookRes));
                response = Response.noContent().build();
            }
        } catch (Exception ex) {
            log.error("books rest: putJson - ",ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }
}
