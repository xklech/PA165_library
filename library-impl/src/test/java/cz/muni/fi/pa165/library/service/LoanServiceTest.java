package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michal Sukupčák
 */
@Service
@Transactional
public class LoanServiceTest extends AbstractIntegrationTest{
    

    private LoanServiceImpl loanService;
    
    private LoanDao loanDaoMock;
    
    private CustomerDao customerDaoMock;
    
    @Before
    public void initTest() {
	this.loanService = new LoanServiceImpl();
	this.loanDaoMock = mock(LoanDao.class);
	this.customerDaoMock = mock(CustomerDao.class);
        this.loanService.setCustomerDao(customerDaoMock);
        this.loanService.setLoanDao(loanDaoMock);
    }
    
    @Test
    public void testAddLoan() throws Exception {
	CustomerTo customerTo = new CustomerTo();
	customerTo.setId(1l);
	customerTo.setFirstName("Cheesus");
	customerTo.setLastName("Crist");
	customerTo.setAddress("Nazareth 1/135");
	customerTo.setDateOfBirth(this.parseDate("24/12/0001"));
	customerTo.setPid("1");
	BookTo bookTo = new BookTo();
	bookTo.setId(1l);
	bookTo.setName("Bible");
	bookTo.setIsbn("1");
	bookTo.setDepartment("Religious studies");
	bookTo.setPublishDate(this.parseDate("1/1/1"));
	bookTo.setAuthor("God");
	ImpressionTo impressionTo = new ImpressionTo();
	impressionTo.setId(1l);
	impressionTo.setBookTo(bookTo);
	impressionTo.setInitialDamage(DamageType.NEW);
	impressionTo.setDamage(DamageType.HEAVILY_DAMAGED);
	impressionTo.setStatus(StatusType.LOANED);
	LoanTo loanTo = new LoanTo();
	loanTo.setId(1l);
	loanTo.setCustomerTo(customerTo);
	loanTo.setImpressionTo(impressionTo);
	loanTo.setFromDate(this.parseDate("12/1/2012"));
	loanTo.setToDate(this.parseDate("12/1/2013"));
	loanTo.setDamageType(DamageType.DAMAGED);
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	doAnswer(new Answer() {
	    @Override
	    public Object answer(InvocationOnMock invocation) {
		Object[] args = invocation.getArguments();
		Loan loan = (Loan) args[0];
		loan.setId(1l);
		return null;
	    }
	}).when(this.loanDaoMock).addLoan(loan);
	this.loanService.addLoan(loanTo);
	assertNotNull(loanTo.getId());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testUpdateLoan() throws Exception {
        LoanTo loanTo = new LoanTo(new CustomerTo(),new ImpressionTo(),this.parseDate("08/08/2008"),null,DamageType.USED);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
        doThrow(IllegalArgumentException.class).when(this.loanDaoMock).updateLoan(loan);        
        this.loanService.updateLoan(loanTo);
        doThrow(IllegalArgumentException.class).when(this.loanDaoMock).updateLoan(null);
        this.loanService.deleteLoan(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testDeleteLoan() throws Exception {
        LoanTo loanTo = new LoanTo(new CustomerTo(),new ImpressionTo(),this.parseDate("08/08/2008"),null,DamageType.USED);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
        doThrow(IllegalArgumentException.class).when(this.loanDaoMock).deleteLoan(loan);        
        this.loanService.deleteLoan(loanTo);
        doThrow(IllegalArgumentException.class).when(this.loanDaoMock).deleteLoan(null);
        this.loanService.deleteLoan(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFindLoanById() throws Exception {
        LoanTo loanTo = new LoanTo(new CustomerTo(),new ImpressionTo(),this.parseDate("08/08/2008"),null,DamageType.USED);
        loanTo.setId(1l);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
        when(loanDaoMock.findLoanById(1l)).thenReturn(loan);
        LoanTo loanTo2 = loanService.findLoanById(1l);
        Assert.assertEquals(loanTo, loanTo2);
        
        when(loanDaoMock.findLoanById(123l)).thenReturn(null);
        LoanTo loanTo3 = loanService.findLoanById(123l);
        Assert.assertNull(loanTo3);
        when(loanDaoMock.findLoanById(null)).thenThrow(IllegalArgumentException.class);
        this.loanService.findLoanById(null);
    }
    
    @Test
    public void testFindAllActiveLoans() throws Exception {
        LoanTo loanTo = new LoanTo(new CustomerTo(),new ImpressionTo(),this.parseDate("08/08/2008"),null,DamageType.USED);
        loanTo.setId(1l);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
        when(loanDaoMock.findAllActiveLoans()).thenReturn(Arrays.asList(loan));
        Collection<LoanTo> loanTo2 = loanService.findAllActiveLoans();
        Assert.assertEquals(Arrays.asList(loanTo), loanTo2);
        
        when(loanDaoMock.findAllActiveLoans()).thenReturn(null);
        Collection<LoanTo> loanTo3 = loanService.findAllActiveLoans();
        Assert.assertNull(loanTo3);
    }
    
    
    
    private Date parseDate(String date) {
	Date d = null;
	SimpleDateFormat smf = new SimpleDateFormat("d/M/y");
	try {	
	    d = smf.parse(date);
	} catch (ParseException ex) {
	    System.err.println("Error: Invalid date format '" + date + "' in LoanTest.parseDate() unit test!");
	} finally {
	    return d;
	}
    }
    
}
