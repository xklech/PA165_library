package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import java.util.List;

/**
 *
 * @author Ivana Haraslínová
 */
public interface CustomerService {
    
    /** 
     * Adds given customer.
     * 
     * @param customerTO 
     * @return CustomerTo if save was successful, null otherwise
     */
    public CustomerTo addCustomer(CustomerTo customerTO);
    
    /**
     * Updates given customer.
     * 
     * @param customerTO 
     * @return true if customer was deleted, false otherwise
     */
    public CustomerTo updateCustomer(CustomerTo customerTO);
    
    /**
     * Deletes given customer.
     * 
     * @param customerTO 
     * @return CustomerTo if update was successful, null otherwise
     */
    public boolean deleteCustomer(CustomerTo customerTO);

    /**
     * Finds customer with given id.
     * 
     * @param id of searched customer
     * @return  CustomerTo
     */
    public CustomerTo findCustomerById(Long id);
    
    /**
     * Finds all customers database.
     * 
     * @return customers
     */
    public List<CustomerTo> findAllCustomers();
    
    /**
     * Finds all customers which have borrowed given book
     * 
     * @param bookTO
     * @return customers
     */
    public List<CustomerTo> findCustomersByBook(BookTo bookTO);
    
    /**
     * Finds customer with given loan
     * 
     * @param loan
     * @return customers
     */
    public CustomerTo findCustomerByLoan(LoanTo loan);
    
    /**
     * Finds customer with given name
     * 
     * @param firstName
     * @param lastName
     * @return customers
     */
    public List<CustomerTo> findCustomerByName(String firstName, String lastName);    
}
