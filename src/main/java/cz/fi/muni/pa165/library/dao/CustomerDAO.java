package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Customer;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.exceptions.CustomerDaoException;
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
     * @throws CustomerDaoException thrown when customer parameter is null or 
     *                              has id with non-null value                   
     */
    public void addCustomer(Customer customer) throws CustomerDaoException;
    
    /**
     * Update customer when he borrows or returns a book.
     * 
     * @param customer to be updated
     * @throws CustomerDaoException thrown when customer parameter is null 
     *                              or id is null
     */
    public void updateCustomer(Customer customer) throws CustomerDaoException;
    
    /**
     * Delets given customer.
     * 
     * @param customer to be deleted
     * @throws CustomerDaoException thrown when customer parameter is null 
     *                              or id is null
     */
    public void deleteCustomer(Customer customer) throws CustomerDaoException;
    
    /**
     * Finds customer with given id.
     * 
     * @param id of searched customer
     * @throws CustomerDaoException thrown when customers id is null
     */
    public Customer findCustomerById(Long id) throws CustomerDaoException;
    
    /**
     * Finds all customers database.
     * 
     */
    public Collection<Customer> findAllCustomers();
    
    /**
     * Finds all customers which have borrowed given book
     * 
     * @throws CustomerDaoException thrown when book parameter is null 
     *                              or its id is null
     */
    public Collection<Customer> findCustomersByBook(Book book) throws CustomerDaoException;
    
    /**
     * Finds customer with given loan
     * 
     * @throws CustomerDaoException thrown when loan parameter is null 
     *                              or its id is null
     */
    public Collection<Customer> findCustomerByLoan(Loan loan) throws CustomerDaoException;
    
    /**
     * Finds customer with given name
     * 
     * @throws CustomerDaoException thrown when firstName parameter or lastName 
     *                              parameter is null
     */
    public Collection<Customer> findCustomerByName(String firstName, String lastName) throws CustomerDaoException;
    
}
