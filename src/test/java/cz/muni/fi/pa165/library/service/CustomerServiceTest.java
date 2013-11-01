package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;

import cz.muni.fi.pa165.library.to.CustomerTO;
import cz.muni.fi.pa165.library.to.LoanTO;
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
        CustomerTO customerTO = new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
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
   
    @Test(expected=ServiceDataAccessException.class)
    public void testDeleteCustomer() throws Exception {
        CustomerTO customerTO= new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).deleteCustomer(customer);        
        customerService.deleteCustomer(customerTO);

        
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).deleteCustomer(null);
        customerService.deleteCustomer(null);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testUpdateCustomer() throws Exception {
        CustomerTO customerTO= new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).updateCustomer(customer);        
        customerService.updateCustomer(customerTO);

        
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).updateCustomer(null);
        customerService.updateCustomer(null);
    }
    
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindCustomerById() throws Exception {
        CustomerTO customerTO= new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        customerTO.setId(12l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);

        when(mockedCustomerDao.findCustomerById(12l)).thenReturn(customer);
        CustomerTO customerTO2 = customerService.findCustomerById(1l);
        assertEquals(customerTO, customerTO2);

        when(mockedCustomerDao.findCustomerById(1l)).thenReturn(null);

        assertNull(customerService.findCustomerById(1l));
        
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).findCustomerById(null);
        customerService.findCustomerById(null);
    }    
    
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindCustomerByLoan()throws Exception { 
        CustomerTO customerTO = new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        customerTO.setId(12l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);

        LoanTO loanTO = new LoanTO();
        loanTO.setCustomerTo(customerTO);
        loanTO.setId(12l);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTO);
        
        when(mockedCustomerDao.findCustomerByLoan(loan)).thenReturn(Arrays.asList(customer));
        when(mockedLoanDao.findLoanById(loan.getId())).thenReturn(loan);
        
        Collection<CustomerTO> customersTO = customerService.findCustomerByLoan(loanTO);
        assertEquals(Arrays.asList(customerTO), customersTO);
        
        LoanTO loanTO2 = new LoanTO();
        Loan loan2 = EntityConvertor.convertFromLoanTo(loanTO2);

        when(mockedCustomerDao.findCustomerByLoan(loan2)).thenReturn(null);
        assertNull(customerService.findCustomerByLoan(loanTO2));
        
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).findCustomerByLoan(null);
        customerService.findCustomerByLoan(null);
        
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindAllCustomers() throws Exception {
        
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindCustomersByBook() throws Exception {
        
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindCustomerByName()throws Exception {
        CustomerTO customerTO = new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        customerTO.setId(12l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);

        when(mockedCustomerDao.findCustomerByName("George", "White")).thenReturn(Arrays.asList(customer));
        Collection<CustomerTO> customersTO = customerService.findCustomerByName("George", "White");
        assertEquals(Arrays.asList(customerTO), customersTO);

        when(mockedCustomerDao.findCustomerByName("George","")).thenReturn(null);

        assertNull(customerService.findCustomerByName("George",""));
        
        doThrow(ServiceDataAccessException.class).when(mockedCustomerDao).findCustomerByName(null,null);
        customerService.findCustomerByName(null,null);
        
    }    
 
}
