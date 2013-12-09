package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import java.util.Collection;

/**
 * Data Access Object for managing entity Customer.
 *
 * @author Michal Sukupčák
 */
public interface CustomerDao {
    
    /**
     * Saves new customer.
     * 
     * @param customer to be saved                 
     */
    public void addCustomer(Customer customer);
    
    /**
     * Updates given customer.
     * 
     * @param customer to be updated
     */
    public void updateCustomer(Customer customer);
    
    /**
     * Deletes given customer.
     * 
     * @param customer to be deleted
     */
    public void deleteCustomer(Customer customer);
    
    /**
     * Finds customer with given id.
     * 
     * @param id of searched customer
     * @return customer
     */
    public Customer findCustomerById(Long id);
    
    /**
     * Finds all customers database.
     * 
     * @return customers
     */
    public Collection<Customer> findAllCustomers();
    
    /**
     * Finds all customers which have borrowed given book
     * 
     * @param book
     * @return customers
     */
    public Collection<Customer> findCustomersByBook(Book book);
    
    /**
     * Finds customer with given loan
     * 
     * @param loan
     * @return customer
     */
    public Customer findCustomerByLoan(Loan loan);
    
    /**
     * Finds customer with given name
     * 
     * @param firstName
     * @param lastName
     * @return customers
     */
    public Collection<Customer> findCustomerByName(String firstName, String lastName);
    
}
