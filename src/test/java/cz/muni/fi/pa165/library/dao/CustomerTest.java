package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.entity.Customer;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for customerDAO implementation
 *
 * @author Michal Sukupčák
 */
public class CustomerTest extends AbstractIntegrationTest{
    
    @Autowired
    private CustomerDao customerDao;
    
    @Test
    public void testAddCustomer() throws Exception {
	Customer customer = new Customer(null, "John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973", null);
        Customer customerSaved;
 
        customerDao.addCustomer(customer);

        customerSaved = customerDao.findCustomerById(customer.getId());
        Assert.assertEquals(customer, customerSaved);
    }
    
    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer1 = new Customer(null, "John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973", null);
        customerDao.addCustomer(customer1);
        
        
        Customer customer = customerDao.findCustomerById(customer1.getId());
        customer.setFirstName("George");
        
        Customer customer2 = new Customer(null, "John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973", null);
        customer2.setId(customer.getId());
        customer2.setFirstName("George");
        customerDao.updateCustomer(customer2);

        Customer customer3 = customerDao.findCustomerById(customer.getId());
        Assert.assertEquals("George",customer3.getFirstName());

    }
    
    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer1 = new Customer(null, "John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973", null);

        customerDao.addCustomer(customer1);
        
    	Customer customer = customerDao.findCustomerById(customer1.getId());

        customerDao.deleteCustomer(customer);


        Customer customer2 = customerDao.findCustomerById(customer1.getId());
        Assert.assertNull(customer2);

    }
    
    @Test
    public void testFindCustomerById() throws Exception {
        Customer customer1 = new Customer(null, "John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973", null);

        customerDao.addCustomer(customer1);

        Assert.assertEquals(customer1, customerDao.findCustomerById(customer1.getId()));
        
        Customer customer3 = customerDao.findCustomerById(Long.MAX_VALUE);
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
