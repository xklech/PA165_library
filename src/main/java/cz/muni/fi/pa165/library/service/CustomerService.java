package cz.muni.fi.pa165.library.service;


import cz.muni.fi.pa165.library.to.CustomerTO;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.LoanTO;
import java.util.Collection;

/**
 *
 * @author Ivana Haraslínová
 */
public interface CustomerService {
    
    /** 
     * Adds given customer.
     * 
     * @param customerTO 
     */
    public void addCustomer(CustomerTO customerTO);
    
    /**
     * Delets given customer.
     * 
     * @param customerTO 
     */
    public void deleteCustomer(CustomerTO customerTO);
    
    /**
     * Updates given customer.
     * 
     * @param customerTO 
     */
    public void updateCustomer(CustomerTO customerTO);

    /**
     * Finds customer with given id.
     * 
     * @param id of searched customer
     */
    public CustomerTO findCustomerById(Long id);
    
    /**
     * Finds all customers database.
     * 
     */
    public Collection<CustomerTO> findAllCustomers();
    
    /**
     * Finds all customers which have borrowed given book
     * 
     */
    public Collection<CustomerTO> findCustomersByBook(BookTo bookTO);
    
    /**
     * Finds customer with given loan
     * 
     */
    public Collection<CustomerTO> findCustomerByLoan(LoanTO loan);
    
    /**
     * Finds customer with given name
     * 
     */
    public Collection<CustomerTO> findCustomerByName(String firstName, String lastName);    
}
