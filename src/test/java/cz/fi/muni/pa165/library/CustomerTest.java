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
	Customer customer = new Customer((new Long(2350), "John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973", null);
        Customer customerSaved;
        em.getTransaction().begin();
        customerDAO.addCustomer(customer);
        em.getTransaction().commit();
        customerSaved = em.find(Customer.class, customer.getId());
        Assert.assertEquals(customer, customerSaved);
    }
    
    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = em.find(Customer.class,new Long(1));
        customer.setFirstName("George");
        em.getTransaction().begin();
        customerDAO.updateCustomer(customer);
        em.getTransaction().commit();
        em.clear();
        Customer customer2 = em.find(Customer.class,new Long(1));
        Assert.assertEquals("George",customer2.getFirstName());

    }
    
    @Test
    public void testDeleteCustomer() throws Exception {
    	Customer customer = em.find(Customer.class,new Long(1));
        em.getTransaction().begin();
        customerDAO.deleteCustomer(customer);
        em.getTransaction().commit();
        em.clear();
        Customer customer2 = em.find(Customer.class,new Long(1));
        Assert.assertNull(customer2);

    }
    
    @Test
    public void testFindCustomerById() throws Exception {
	Customer customer = em.find(Customer.class,new Long(2));
        Customer customer2 = customerDAO.findCustomerById(new Long(2));
        Assert.assertEquals(customer, customer2);
        
        Customer customer3 = customerDAO.findCustomerById(new Long(754));
        Assert.assertNull(customer3);
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
