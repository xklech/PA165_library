/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public void addCustomer(Customer customer) throws CustomerDAOException;
    
    public void updateCustomer(Customer customer) throws CustomerDAOException;
    
    public void deleteCustomer(Customer customer) throws CustomerDAOException;
    
    public Customer findCustomerById(Long id) throws CustomerDAOException;
    
    public Collection<Customer> findAllCustomers();
    
    public Collection<Customer> findCustomersByBook(Book book) throws CustomerDAOException;
    
    public Collection<Customer> findCustomerByLoan(Loan loan) throws CustomerDAOException;
    
    public Collection<Customer> findCustomerByName(String firstName, String lastName) throws CustomerDAOException;
    
}
