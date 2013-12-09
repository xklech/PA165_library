package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.CustomerDaoException;
import java.util.Arrays;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * Unit test for customerDAO implementation
 *
 * @author Michal Sukupčák
 */
public class CustomerTest extends AbstractIntegrationTest{
    
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LoanDao loanDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ImpressionDao impressionDao;
    
    @Test(expected=DataAccessException.class)
    public void testAddCustomer() throws Exception {
	Customer customer = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        Customer customerSaved;
 
        customerDao.addCustomer(customer);

        customerSaved = customerDao.findCustomerById(customer.getId());
        Assert.assertEquals(customer, customerSaved);
        
        customerDao.addCustomer(customer);
    }
    
    @Test(expected=DataAccessException.class)
    public void testUpdateCustomer() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        customerDao.addCustomer(customer1);
        
        
        Customer customer = customerDao.findCustomerById(customer1.getId());
        customer.setFirstName("George");
        
        Customer customer2 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        customer2.setId(customer.getId());
        customer2.setFirstName("George");
        customerDao.updateCustomer(customer2);

        Customer customer3 = customerDao.findCustomerById(customer.getId());
        Assert.assertEquals("George",customer3.getFirstName());
        
        customerDao.updateCustomer(null);

    }
    
    @Test(expected=DataAccessException.class)
    public void testDeleteCustomer() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");

        customerDao.addCustomer(customer1);
        
    	Customer customer = customerDao.findCustomerById(customer1.getId());

        customerDao.deleteCustomer(customer);


        Customer customer2 = customerDao.findCustomerById(customer1.getId());
        Assert.assertNull(customer2);
        
        customerDao.deleteCustomer(null);

    }
    
    @Test(expected=DataAccessException.class)
    public void testFindCustomerById() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");

        customerDao.addCustomer(customer1);

        Assert.assertEquals(customer1, customerDao.findCustomerById(customer1.getId()));
        
        Customer customer3 = customerDao.findCustomerById(Long.MAX_VALUE);
        Assert.assertNull(customer3);
        
        customerDao.findCustomerById(null);
    }
    
    @Test
    public void testFindAllCustomers() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        Customer customer2 = new Customer("John", "Pepa", "1 New Oxford Street, London", new Date(23-06-1989), "830623/6973");
        Assert.assertEquals(0, customerDao.findAllCustomers().size());
        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);
        Assert.assertEquals(Arrays.asList(customer1,customer2),customerDao.findAllCustomers());
    }
    
    @Test(expected=DataAccessException.class)
    public void testFindCustomersByBook() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        customerDao.addCustomer(customer1);
        Book book = new Book("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookDao.addBook(book);
        Impression impression = new Impression(book, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        impressionDao.addImpression(impression);
        Loan loan = new Loan();
        loan.setCustomer(customer1);
        loan.setImpression(impression);
        loanDao.addLoan(loan);
        
        Assert.assertEquals(Arrays.asList(customer1),customerDao.findCustomersByBook(book));
        
        customerDao.findCustomersByBook(null);
    }
    
    public void testFindCustomerByLoan() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        customerDao.addCustomer(customer1);
        Loan loan = new Loan();
        loan.setCustomer(customer1);
        loanDao.addLoan(loan);
        
        Assert.assertEquals(customer1,customerDao.findCustomerByLoan(loan));
    }
    
    @Test(expected=DataAccessException.class)
    public void testFindCustomerByName() throws Exception {
        Customer customer1 = new Customer("John", "Smith", "1 New Oxford Street, London", new Date(23-06-1983), "830623/6973");
        Customer customer2 = new Customer("John", "Pepa", "1 New Oxford Street, London", new Date(23-06-1989), "830623/6973");
        Assert.assertEquals(0, customerDao.findAllCustomers().size());
        customerDao.addCustomer(customer1);
        customerDao.addCustomer(customer2);
        
        Assert.assertEquals(Arrays.asList(customer1,customer2),customerDao.findCustomerByName("John", null));
        Assert.assertEquals(Arrays.asList(customer1),customerDao.findCustomerByName("John", "Smith"));
        
        customerDao.findCustomerByName(null, null);
    }
    
}
