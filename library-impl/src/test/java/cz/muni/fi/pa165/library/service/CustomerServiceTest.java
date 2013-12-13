package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.to.BookTo;

import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.Arrays;
import java.util.Collection;

import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 *
 * @author Ivana Haraslínová
 */
public class CustomerServiceTest {
    
    private CustomerServiceImpl customerService;
    
    private CustomerDao mockedCustomerDao;
    
    private LoanDao mockedLoanDao;
    
    private BookDao mockedBookDao;
    
    @Before
    public void initTest(){
        customerService = new CustomerServiceImpl();
        mockedCustomerDao = mock(CustomerDao.class);
        mockedLoanDao = mock(LoanDao.class);
        mockedBookDao = mock (BookDao.class);
        
        customerService.setCustomerDao(mockedCustomerDao);
        customerService.setLoanDao(mockedLoanDao);
        customerService.setBookDao(mockedBookDao);
    }
    
    @Test
    public void testAddCustomer() throws Exception {
        CustomerTo customerTO = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Customer customerDao = (Customer)args[0];
                customerDao.setId(1l);
                return null;
            }
        })
        .when(mockedCustomerDao).addCustomer(customer);
        customerService.addCustomer(customerTO);
        assertNotNull(customerTO.getId());
    }
   
    @Test(expected=IllegalArgumentException.class)
    public void testDeleteCustomer() throws Exception {
        CustomerTo customerTO= new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).deleteCustomer(customer);        
        customerService.deleteCustomer(customerTO);
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).deleteCustomer(null);
        customerService.deleteCustomer(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testUpdateCustomer() throws Exception {
        CustomerTo customerTO= new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).updateCustomer(customer);        
        customerService.updateCustomer(customerTO);
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).updateCustomer(null);
        customerService.updateCustomer(null);
    }
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testFindCustomerById() throws Exception {
        CustomerTo customerTO= new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        customerTO.setId(12l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        when(mockedCustomerDao.findCustomerById(12l)).thenReturn(customer);
        CustomerTo customerTO2 = customerService.findCustomerById(12l);
        assertEquals(customerTO, customerTO2);
        when(mockedCustomerDao.findCustomerById(1l)).thenReturn(null);
        assertNull(customerService.findCustomerById(1l));
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).findCustomerById(null);
        customerService.findCustomerById(null);
    }    
    
    
    @Test(expected=IllegalArgumentException.class)
    public void testFindCustomerByLoan()throws Exception { 
        CustomerTo customerTO = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        customerTO.setId(12l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);

        LoanTo loanTO = new LoanTo();
        loanTO.setCustomerTo(customerTO);
        loanTO.setId(12l);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTO);
        
        when(mockedCustomerDao.findCustomerByLoan(loan)).thenReturn(customer);
        when(mockedLoanDao.findLoanById(loan.getId())).thenReturn(loan);
        
        CustomerTo customersTO = customerService.findCustomerByLoan(loanTO);
        assertEquals(customerTO, customersTO);
        
        LoanTo loanTO2 = new LoanTo();
        Loan loan2 = EntityConvertor.convertFromLoanTo(loanTO2);

        when(mockedCustomerDao.findCustomerByLoan(loan2)).thenReturn(null);
        assertNull(customerService.findCustomerByLoan(loanTO2));
        
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).findCustomerByLoan(null);
        customerService.findCustomerByLoan(null);
        
    }
    
    @Test
    public void testFindAllCustomers() throws Exception {
        CustomerTo customerTo1 = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customerTo1.setId(11l);
        Customer customer1 = EntityConvertor.convertFromCustomerTo(customerTo1);

        CustomerTo customerTo2 = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customerTo2.setId(12l);
        Customer customer2 = EntityConvertor.convertFromCustomerTo(customerTo2);    
        when(mockedCustomerDao.findAllCustomers()).thenReturn(null);      
        assertNull(customerService.findAllCustomers());
        
        when(mockedCustomerDao.findAllCustomers()).thenReturn(Arrays.asList(customer1,customer2));      
        Collection<CustomerTo> customers = customerService.findAllCustomers();
        assertEquals(customers, Arrays.asList(customerTo1,customerTo2));
        
        
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFindCustomersByBook() throws Exception {
        CustomerTo customerTo1 = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customerTo1.setId(11l);
        Customer customer1 = EntityConvertor.convertFromCustomerTo(customerTo1); 
        CustomerTo customerTo2 = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customerTo2.setId(12l);
        Customer customer2 = EntityConvertor.convertFromCustomerTo(customerTo2);    
        
        Book book = new Book("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        book.setId(1l);
        BookTo bookTo = EntityConvertor.convertFromBook(book);
        when(mockedBookDao.findBookById(book.getId())).thenReturn(book);      
        when(mockedCustomerDao.findCustomersByBook(book)).thenReturn(Arrays.asList(customer1,customer2));      
        Collection<CustomerTo> customers = customerService.findCustomersByBook(bookTo);
        assertEquals(customers, Arrays.asList(customerTo1,customerTo2));
        
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).findCustomersByBook(null);
        customerService.findCustomerByLoan(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFindCustomerByName()throws Exception {
        CustomerTo customerTO = new CustomerTo( "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        customerTO.setId(12l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);

        when(mockedCustomerDao.findCustomerByName("George", "White")).thenReturn(Arrays.asList(customer));
        Collection<CustomerTo> customersTO = customerService.findCustomerByName("George", "White");
        assertEquals(Arrays.asList(customerTO), customersTO);

        when(mockedCustomerDao.findCustomerByName("George","")).thenReturn(null);

        assertNull(customerService.findCustomerByName("George",""));
        
        doThrow(IllegalArgumentException.class).when(mockedCustomerDao).findCustomerByName(null,null);
        customerService.findCustomerByName(null,null);
        
    }    
 
}
