/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.LoanDAO;
import cz.fi.muni.pa165.library.dao.LoanDAOImpl;
import cz.fi.muni.pa165.library.entity.Customer;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.enums.ConditionType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for LoanDAO implementation
 *
 * @author Michal Sukupčák
 */
public class LoanTest {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    private LoanDAO loanDAO; 
    
    public LoanTest() {}

    @Before
    public void setUp() {
	this.emf = Persistence.createEntityManagerFactory("LibraryPU");
	this.em = emf.createEntityManager();
	this.loanDAO = new LoanDAOImpl(this.em);
	Date date1From = null;
	Date date1To = null;
	Date date2From = null;
	Date date2To = null;
	Date date3From = null;
	Date date3To = null;
	SimpleDateFormat smf = new SimpleDateFormat("DD/MM/YYYY");
	try {
	    date1From = smf.parse("01/01/2013");
	    date1To = smf.parse("02/02/2013");
	    date2From = smf.parse("03/03/2013");
	    date2To = smf.parse("04/04/2013");
	    date3From = smf.parse("05/05/2013");
	    date3To = null;
	} catch (ParseException ex) {
	    System.err.println("Error: Invalid date in LoanTest.setUp() unit test!");
	}
	Customer customer1 = new Customer();
	Impression impression1 = new Impression();
	Impression impression2 = new Impression();
	Impression impression3 = new Impression();
	Loan loan1 = new Loan(customer1,impression1,date1From,date1To,ConditionType.USED);
	Loan loan2 = new Loan(customer1,impression2,date2From,date2To,ConditionType.SLIGHTLY_DAMAGED);
	Loan loan3 = new Loan(customer1,impression3,date3From,date3To,null);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan1);
	loans.add(loan2);
	loans.add(loan3);
	customer1.setLoans(loans);
	this.em.getTransaction().begin();
	this.em.persist(loan1);
	this.em.persist(loan2);
	this.em.persist(loan3);
	this.em.getTransaction().commit();
    }
    
    @After
    public void tearDown() {
	this.em.close();
	this.emf.close();
    }
    
    @Test
    public void testAddLoan() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("01/01/2001"),this.parseDate("02/02/2002"),ConditionType.DAMAGED);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan);
	customer.setLoans(loans);
	this.em.getTransaction().begin();
	this.loanDAO.addLoan(loan);
	this.em.getTransaction().commit();
	Loan loanFromDatabase = this.em.find(Loan.class,loan.getId());
	Assert.assertEquals(loan,loanFromDatabase);
    }
    
    @Test
    public void testUpdateLoan() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("03/03/2003"),null,null);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan);
	customer.setLoans(loans);
	this.em.getTransaction().begin();
	this.em.persist(loan);
	this.em.getTransaction().commit();
	Date newDate = this.parseDate("04/04/2004");
	Loan loanToUpdate = this.em.find(Loan.class,loan.getId());
	loanToUpdate.setFromDate(newDate);
	this.em.getTransaction().begin();
	this.loanDAO.updateLoan(loanToUpdate);
	this.em.getTransaction().commit();
	Loan updatedLoan = this.em.find(Loan.class,loan.getId());
	Assert.assertEquals(newDate,updatedLoan.getFromDate());
    }
    
    @Test
    public void testDeleteLoan() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("01/01/2001"),this.parseDate("02/02/2002"),ConditionType.DAMAGED);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan);
	customer.setLoans(loans);
	this.em.getTransaction().begin();
	this.em.persist(loan);
	this.em.getTransaction().commit();
	this.em.getTransaction().begin();
	this.loanDAO.deleteLoan(loan);
	this.em.getTransaction().commit();
	this.em.clear();
	Loan ldb1 = this.em.find(Loan.class,loan.getId());
	Assert.assertNull(ldb1);
    }
    
    @Test
    public void testFindLoanById() throws Exception {
	Long fakeId = new Long(Long.MAX_VALUE);
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("05/05/2005"),null,null);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan);
	customer.setLoans(loans);
	this.em.getTransaction().begin();
	this.em.persist(loan);
	this.em.getTransaction().commit();
	this.em.getTransaction().begin();
	Loan loanFromDatabase1 = this.loanDAO.findLoanById(loan.getId());
	Loan loanFromDatabase2 = this.loanDAO.findLoanById(loan.getId());
	Loan loanFromDatabaseFake = this.em.find(Loan.class,fakeId);
	this.em.getTransaction().commit();
	Assert.assertEquals(loanFromDatabase1,loanFromDatabase2);
	Assert.assertNull(loanFromDatabaseFake);
    }
    
    @Test
    public void testFindAllActiveLoans() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("06/06/2006"),null,null);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan);
	customer.setLoans(loans);
	this.em.getTransaction().begin();
	this.em.persist(loan);
	this.em.getTransaction().commit();
	this.em.getTransaction().begin();
	Collection<Loan> loansFromDatabase = this.loanDAO.findAllActiveLoans();
	this.em.getTransaction().commit();
	for (Loan loanFromDatabase : loansFromDatabase) {
	    Assert.assertNull(loanFromDatabase.getToDate());
	}
    }
    
    @Test
    public void testFindLoansByCustomer() throws Exception {
	Customer customer = new Customer();
	Loan loan = new Loan(customer,new Impression(),this.parseDate("07/07/2007"),this.parseDate("08/08/2008"),ConditionType.DAMAGED);
	Collection<Loan> loans = new ArrayList();
	loans.add(loan);
	customer.setLoans(loans);
	this.em.getTransaction().begin();
	this.em.persist(loan);
	this.em.getTransaction().commit();
	this.em.getTransaction().begin();
	Collection<Loan> loansFromDatabase = this.loanDAO.findLoansByCustomer(customer);
	this.em.getTransaction().commit();
	for (Loan loanFromDatabase : loansFromDatabase) {
	    Assert.assertEquals(loanFromDatabase.getCustomer(),customer);
	}
    }
    
    @Test
    public void testFindLoansByFromTo() throws Exception {
	Date from = this.parseDate("09/09/2009");
	Date to = this.parseDate("10/10/2010");
	Customer customer = new Customer();
	Loan loan1 = new Loan(customer,new Impression(),this.parseDate("01/01/2010"),this.parseDate("02/01/2010"),ConditionType.USED);
	Loan loan2 = new Loan(customer,new Impression(),this.parseDate("01/02/2010"),this.parseDate("02/02/2010"),ConditionType.USED);
	Loan loan3 = new Loan(customer,new Impression(),this.parseDate("01/03/2010"),this.parseDate("02/03/2010"),ConditionType.USED);
	Loan loan4 = new Loan(customer,new Impression(),this.parseDate("01/04/2010"),this.parseDate("02/04/2010"),ConditionType.USED);
	Loan loan5 = new Loan(customer,new Impression(),this.parseDate("01/01/1990"),this.parseDate("02/01/1990"),ConditionType.USED);
	Loan loan6 = new Loan(customer,new Impression(),this.parseDate("09/10/2010"),this.parseDate("11/10/2010"),ConditionType.USED);
	Loan loan7 = new Loan(customer,new Impression(),this.parseDate("08/09/2009"),this.parseDate("10/09/2009"),ConditionType.USED);
	this.em.getTransaction().begin();
	this.em.persist(loan1);
	this.em.persist(loan2);
	this.em.persist(loan3);
	this.em.persist(loan4);
	this.em.persist(loan5);
	this.em.persist(loan6);
	this.em.persist(loan7);
	this.em.getTransaction().commit();
	this.em.getTransaction().begin();
	Collection<Loan> loansFromDatabase = this.loanDAO.findLoansByFromTo(from,to);
	this.em.getTransaction().commit();
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