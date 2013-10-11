package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.CustomerDAO;
import cz.fi.muni.pa165.library.dao.CustomerDAOImpl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for LoanDAO implementation
 *
 * @author Michal Sukupčák
 */
public class CustomerTest {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    private CustomerDAO customerDAO; 
    
    public CustomerTest() {}

    @Before
    public void CustomerTest() {
	this.emf = Persistence.createEntityManagerFactory("LibraryPU");
	this.em = emf.createEntityManager();
	this.customerDAO = new CustomerDAOImpl(this.em);
	
    }
    
    @After
    public void tearDown() {
	this.em.close();
	this.emf.close();
    }
    
    @Test
    public void testAddCustomer() throws Exception {

    }
    
    @Test
    public void testUpdateCustomer() throws Exception {

    }
    
    @Test
    public void testDeleteCustomer() throws Exception {

    }
    
    @Test
    public void testFindCustomerById() throws Exception {

    }
    
    @Test
    public void testFindAllCustomers() throws Exception {

    }
    
    @Test
    public void testFindCustomersByBook() throws Exception {

    }
    
    @Test
    public void testFindCustomerByLoan() throws Exception {

    }
    
    @Test
    public void testFindCustomerByName() throws Exception {

    }
    
}