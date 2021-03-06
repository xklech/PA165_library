package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Jaroslav Klech
 */
public class EntityConvertorTest {
    
    @Test
    public void fromBookTest(){
        Book book= new Book("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        book.setId(1l);
        BookTo bookTo = EntityConvertor.convertFromBook(book);
        assertEquals(book.getId(),bookTo.getId());
        assertEquals(book.getAuthor(),bookTo.getAuthor());
        assertEquals(book.getDepartment(),bookTo.getDepartment());
        assertEquals(book.getISBN(),bookTo.getIsbn());
        assertEquals(book.getName(),bookTo.getName());
        assertEquals(book.getPublishDate(),bookTo.getPublishDate());
    }
    
    @Test
    public void fromBookToTest(){
        BookTo bookTo = new BookTo("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        bookTo.setId(1l);
        Book book = EntityConvertor.convertFromBookTo(bookTo);
        assertEquals(book.getId(),bookTo.getId());
        assertEquals(book.getAuthor(),bookTo.getAuthor());
        assertEquals(book.getDepartment(),bookTo.getDepartment());
        assertEquals(book.getISBN(),bookTo.getIsbn());
        assertEquals(book.getName(),bookTo.getName());
        assertEquals(book.getPublishDate(),bookTo.getPublishDate());
    }
    
    @Test
    public void fromImpressionTest(){
        Book book = new Book("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        book.setId(1l);
        Impression impression = new Impression(book, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        impression.setId(1l);
        ImpressionTo impressionTo = EntityConvertor.convertFromImpression(impression);
        assertEquals(impression.getId(),impressionTo.getId());
        assertEquals(impression.getDamage(),impressionTo.getDamage());
        assertEquals(impression.getInitialDamage(),impressionTo.getInitialDamage());
        assertEquals(impression.getStatus(),impressionTo.getStatus());
        assertEquals(impression.getBook().getId(),impressionTo.getBookTo().getId());
    }
    
    @Test
    public void fromImpressionToTest(){
        BookTo bookTo = new BookTo("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        bookTo.setId(1l);
        ImpressionTo impressionTo = new ImpressionTo(1l,bookTo, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo);
        assertEquals(impression.getId(),impressionTo.getId());
        assertEquals(impression.getDamage(),impressionTo.getDamage());
        assertEquals(impression.getInitialDamage(),impressionTo.getInitialDamage());
        assertEquals(impression.getStatus(),impressionTo.getStatus());
        assertEquals(impression.getBook().getId(),impressionTo.getBookTo().getId());
    } 
    
    @Test
    public void fromCustomerTest(){
        Customer customer = new Customer("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customer.setId(1l);
        CustomerTo customerTo = EntityConvertor.convertFromCustomer(customer);
        assertEquals(customer.getId(),customerTo.getId());
        assertEquals(customer.getAddress(),customerTo.getAddress());
        assertEquals(customer.getDateOfBirth(),customerTo.getDateOfBirth());
        assertEquals(customer.getFirstName(),customerTo.getFirstName());
        assertEquals(customer.getLastName(),customerTo.getLastName());
        assertEquals(customer.getPid(),customerTo.getPid());
    } 
    
    @Test
    public void fromCustomerToTest(){
        CustomerTo customerTo = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customerTo.setId(1l);
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTo);
        assertEquals(customer.getId(),customerTo.getId());
        assertEquals(customer.getAddress(),customerTo.getAddress());
        assertEquals(customer.getDateOfBirth(),customerTo.getDateOfBirth());
        assertEquals(customer.getFirstName(),customerTo.getFirstName());
        assertEquals(customer.getLastName(),customerTo.getLastName());
        assertEquals(customer.getPid(),customerTo.getPid());
    } 
    
    @Test
    public void fromLoanTest(){
        Customer customer = new Customer("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customer.setId(1l);
        Book book = new Book("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        book.setId(1l);
        Impression impression = new Impression(book, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        impression.setId(123l);
        Loan loan = new Loan(customer, impression, new Date(123456), new Date(654321), DamageType.NEW);
        LoanTo loanTo = EntityConvertor.convertFromLoan(loan);
        assertEquals(loan.getId(),loanTo.getId());
        assertEquals(loan.getDamageType(),loanTo.getDamageType());
        assertEquals(loan.getFromDate(),loanTo.getFromDate());
        assertEquals(loan.getToDate(),loanTo.getToDate());
        assertEquals(loan.getImpression().getId(),loanTo.getImpressionTo().getId());
        assertEquals(loan.getCustomer().getId(),loanTo.getCustomerTo().getId());
    } 

    @Test
    public void fromLoanToTest(){
        CustomerTo customerTo = new CustomerTo("George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
	customerTo.setId(1l);
        BookTo bookTo = new BookTo("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        bookTo.setId(1l);
        ImpressionTo impressionTo = new ImpressionTo(123l,bookTo, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        LoanTo loanTo = new LoanTo(customerTo, impressionTo, new Date(123456), new Date(654321), DamageType.NEW);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
        assertEquals(loan.getId(),loanTo.getId());
        assertEquals(loan.getDamageType(),loanTo.getDamageType());
        assertEquals(loan.getFromDate(),loanTo.getFromDate());
        assertEquals(loan.getToDate(),loanTo.getToDate());
        assertEquals(loan.getImpression().getId(),loanTo.getImpressionTo().getId());
        assertEquals(loan.getCustomer().getId(),loanTo.getCustomerTo().getId());
    } 
    
}
