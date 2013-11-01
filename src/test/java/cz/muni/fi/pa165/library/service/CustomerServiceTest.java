package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccesException;

import cz.muni.fi.pa165.library.to.CustomerTO;
import cz.muni.fi.pa165.library.utils.EntityConvertor;

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
   
    @Test(expected=ServiceDataAccesException.class)
    public void testDeleteCustomer() throws Exception {
        CustomerTO customerTO= new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doThrow(ServiceDataAccesException.class).when(mockedCustomerDao).deleteCustomer(customer);        
        customerService.deleteCustomer(customerTO);

        
        doThrow(ServiceDataAccesException.class).when(mockedCustomerDao).deleteCustomer(null);
        customerService.deleteCustomer(null);
    }
    
    @Test(expected=ServiceDataAccesException.class)
    public void testUpdateCustomer() throws Exception {
        CustomerTO customerTO= new CustomerTO(null, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        doThrow(ServiceDataAccesException.class).when(mockedCustomerDao).updateCustomer(customer);        
        customerService.updateCustomer(customerTO);

        
        doThrow(ServiceDataAccesException.class).when(mockedCustomerDao).updateCustomer(null);
        customerService.updateCustomer(null);
    }
 
}