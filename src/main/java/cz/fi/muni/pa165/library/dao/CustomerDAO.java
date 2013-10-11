package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Customer;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.exceptions.CustomerDAOException;
import java.util.Collection;

/**
 * Data Access Object for managing entity Customer.
 *
 * @author Michal Sukupčák
 */
public interface CustomerDAO {
    
    /**
     * Saves new customer.
     * 
     * @param customer to be saved
     * @throws CustomerDAOException thrown when customer parameter is null or 
     *                              has id with non-null value                   
     */
    public void addCustomer(Customer customer) throws CustomerDAOException;
    
    /**
     * Update customer when he borrows or returns a book.
     * 
     * @param customer to be updated
     * @throws CustomerDAOException thrown when customer parameter is null 
     *                              or id is null
     */
    public void updateCustomer(Customer customer) throws CustomerDAOException;
    
    /**
     * Delets given customer.
     * 
     * @param customer to be deleted
     * @throws CustomerDAOException thrown when customer parameter is null 
     *                              or id is null
     */
    public void deleteCustomer(Customer customer) throws CustomerDAOException;
    
    /**
     * Finds customer with given id.
     * 
     * @param id of searched customer
     * @throws CustomerDAOException thrown when customers id is null
     */
    public Customer findCustomerById(Long id) throws CustomerDAOException;
    
    /**
     * Finds all customers database.
     * 
     */
    public Collection<Customer> findAllCustomers();
    
    /**
     * Finds all customers which have borrowed given book
     * 
     * @throws CustomerDAOException thrown when book parameter is null 
     *                              or its id is null
     */
    public Collection<Customer> findCustomersByBook(Book book) throws CustomerDAOException;
    
    /**
     * Finds customer with given loan
     * 
     * @throws CustomerDAOException thrown when loan parameter is null 
     *                              or its id is null
     */
    public Collection<Customer> findCustomerByLoan(Loan loan) throws CustomerDAOException;
    
    /**
     * Finds customer with given name
     * 
     * @throws CustomerDAOException thrown when firstName parameter or lastName 
     *                              parameter is null
     */
    public Collection<Customer> findCustomerByName(String firstName, String lastName) throws CustomerDAOException;
    
}
