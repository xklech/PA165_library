package cz.muni.fi.pa165.library.rest;

import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;


public class ResourceConvertor {
        
    public static CustomerTo fromCustomerResource(CustomerResource resource){
        if(resource == null){
            return null;
        }
        CustomerTo customerTo = new CustomerTo();
        customerTo.setAddress(resource.getAddress());
        customerTo.setDateOfBirth(resource.getDateOfBirth());
        customerTo.setFirstName(resource.getFirstName());
        customerTo.setLastName(resource.getLastName());
        customerTo.setId(resource.getId());
        customerTo.setPid(resource.getPid());
        
        
        return customerTo;
    }
    
    public static CustomerResource fromCustomerTo(CustomerTo customerTo){
        if(customerTo == null){
            return null;
        }
        CustomerResource customerResource = new CustomerResource();
        customerResource.setAddress(customerTo.getAddress());
        customerResource.setDateOfBirth(customerTo.getDateOfBirth());
        customerResource.setFirstName(customerTo.getFirstName());
        customerResource.setLastName(customerTo.getLastName());
        customerResource.setId(customerTo.getId());
        customerResource.setPid(customerTo.getPid());
        
        return customerResource;
    }
    
    /**
     * @author Mask
     */
    public static BookTo fromBookResource(BookResource resource){
        if(resource == null){
            return null;
        }
        BookTo bookTo = new BookTo();
        bookTo.setId(resource.getId());
        bookTo.setName(resource.getName());
        bookTo.setAuthor(resource.getAuthor());
        bookTo.setDepartment(resource.getDepartment());
        bookTo.setIsbn(resource.getIsbn());
        bookTo.setPublishDate(resource.getPublishDate());

        return bookTo;
    }
    
    /**
     * @author Mask
     */
    public static BookResource fromBookTo(BookTo bookTo) {
        if(bookTo == null) {
            return null;
        }
        BookResource bookRes = new BookResource();
        bookRes.setId(bookTo.getId());
        bookRes.setName(bookTo.getName());
        bookRes.setAuthor(bookTo.getAuthor());
        bookRes.setDepartment(bookTo.getDepartment());
        bookRes.setIsbn(bookTo.getIsbn());
        bookRes.setPublishDate(bookTo.getPublishDate());
        return bookRes;
    }
        
}
