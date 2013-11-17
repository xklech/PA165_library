package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.enums.DamageType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for LoanDao implementation
 *
 * @author Michal Sukupčák
 */
public class LoanTest extends AbstractIntegrationTest{    

    @Autowired
    private LoanDao loanDao; 
    
    @Test
    public void testAddLoan() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("01/01/2001"),this.parseDate("02/02/2002"),DamageType.DAMAGED);

	loanDao.addLoan(loan);

	Loan loanFromDatabase = loanDao.findLoanById(loan.getId());
	Assert.assertEquals(loan,loanFromDatabase);
    }
    
    @Test
    public void testUpdateLoan() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("03/03/2003"),null,null);

	loanDao.addLoan(loan);

	Date newDate = this.parseDate("04/04/2004");
	Loan loanToUpdate = loanDao.findLoanById(loan.getId());
	loanToUpdate.setFromDate(newDate);

	loanDao.updateLoan(loanToUpdate);

	Loan updatedLoan = loanDao.findLoanById(loan.getId());
	Assert.assertEquals(newDate,updatedLoan.getFromDate());
    }
    
    @Test
    public void testDeleteLoan() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("01/01/2001"),this.parseDate("02/02/2002"),DamageType.DAMAGED);

	loanDao.addLoan(loan);


	loanDao.deleteLoan(loan);


	Loan ldb1 = loanDao.findLoanById(loan.getId());
	Assert.assertNull(ldb1);
    }
    
    @Test
    public void testFindLoanById() throws Exception {
	Long fakeId = new Long(Long.MAX_VALUE);
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("05/05/2005"),null,null);;

	loanDao.addLoan(loan);

	Loan loanFromDatabase1 = loanDao.findLoanById(loan.getId());
	Loan loanFromDatabaseFake = loanDao.findLoanById(fakeId);

	Assert.assertEquals(loanFromDatabase1,loan);
	Assert.assertNull(loanFromDatabaseFake);
    }
    
    @Test
    public void testFindAllActiveLoans() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("06/06/2006"),null,null);

	loanDao.addLoan(loan);

	Collection<Loan> loansFromDatabase = loanDao.findAllActiveLoans();

	Assert.assertEquals(1, loansFromDatabase.size());
    }
    
    @Test
    public void testFindLoansByCustomer() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("07/07/2007"),this.parseDate("08/08/2008"),DamageType.DAMAGED);

	loanDao.addLoan(loan);


	Collection<Loan> loansFromDatabase = loanDao.findLoansByCustomer(customer);

	for (Loan loanFromDatabase : loansFromDatabase) {
	    Assert.assertEquals(loanFromDatabase.getCustomer(),customer);
	}
    }
    
    @Test
    public void testFindLoansByFromTo() throws Exception {
	Date from = this.parseDate("09/09/2009");
	Date to = this.parseDate("10/10/2010");
	Customer customer = new Customer();
	Loan loan1 = new Loan(customer,new Impression(),this.parseDate("01/01/2010"),this.parseDate("02/01/2010"),DamageType.USED);
	Loan loan2 = new Loan(customer,new Impression(),this.parseDate("01/02/2010"),this.parseDate("02/02/2010"),DamageType.USED);
	Loan loan3 = new Loan(customer,new Impression(),this.parseDate("01/03/2010"),this.parseDate("02/03/2010"),DamageType.USED);
	Loan loan4 = new Loan(customer,new Impression(),this.parseDate("01/04/2010"),this.parseDate("02/04/2010"),DamageType.USED);
	Loan loan5 = new Loan(customer,new Impression(),this.parseDate("01/01/1990"),this.parseDate("02/01/1990"),DamageType.USED);
	Loan loan6 = new Loan(customer,new Impression(),this.parseDate("09/10/2010"),this.parseDate("11/10/2010"),DamageType.USED);
	Loan loan7 = new Loan(customer,new Impression(),this.parseDate("08/09/2009"),this.parseDate("10/09/2009"),DamageType.USED);

	loanDao.addLoan(loan1);
	loanDao.addLoan(loan2);
	loanDao.addLoan(loan3);
	loanDao.addLoan(loan4);
	loanDao.addLoan(loan5);
	loanDao.addLoan(loan6);
	loanDao.addLoan(loan7);

	Collection<Loan> loansFromDatabase = this.loanDao.findLoansByFromTo(from,to);

	for (Loan loanFromDatabase : loansFromDatabase) {
	    Assert.assertTrue(loanFromDatabase.getFromDate().after(from) && loanFromDatabase.getToDate().before(to));
	}
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