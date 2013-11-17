package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTO;
import cz.muni.fi.pa165.library.to.ImpressionTO;
import cz.muni.fi.pa165.library.to.LoanTO;
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
        ImpressionTO impressionTo = EntityConvertor.convertFromImpression(impression);
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
        ImpressionTO impressionTo = new ImpressionTO(1l,bookTo, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo);
        assertEquals(impression.getId(),impressionTo.getId());
        assertEquals(impression.getDamage(),impressionTo.getDamage());
        assertEquals(impression.getInitialDamage(),impressionTo.getInitialDamage());
        assertEquals(impression.getStatus(),impressionTo.getStatus());
        assertEquals(impression.getBook().getId(),impressionTo.getBookTo().getId());
    } 
    
    @Test
    public void fromCustomerTest(){
        Customer customer = new Customer(1l, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        CustomerTO customerTo = EntityConvertor.convertFromCustomer(customer);
        assertEquals(customer.getId(),customerTo.getId());
        assertEquals(customer.getAddress(),customerTo.getAddress());
        assertEquals(customer.getDateOfBirth(),customerTo.getDateOfBirth());
        assertEquals(customer.getFirstName(),customerTo.getFirstName());
        assertEquals(customer.getLastName(),customerTo.getLastName());
        assertEquals(customer.getPid(),customerTo.getPid());
    } 
    
    @Test
    public void fromCustomerToTest(){
        CustomerTO customerTo = new CustomerTO(1l, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
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
        Customer customer = new Customer(1l, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        Book book = new Book("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        book.setId(1l);
        Impression impression = new Impression(book, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        impression.setId(123l);
        Loan loan = new Loan(customer, impression, new Date(123456), new Date(654321), DamageType.NEW);
        LoanTO loanTo = EntityConvertor.convertFromLoan(loan);
        assertEquals(loan.getId(),loanTo.getId());
        assertEquals(loan.getDamageType(),loanTo.getDamageType());
        assertEquals(loan.getFromDate(),loanTo.getFromDate());
        assertEquals(loan.getToDate(),loanTo.getToDate());
        assertEquals(loan.getImpression().getId(),loanTo.getImpressionTo().getId());
        assertEquals(loan.getCustomer().getId(),loanTo.getCustomerTo().getId());
    } 

    @Test
    public void fromLoanToTest(){
        CustomerTO customerTo = new CustomerTO(1l, "George", "White", "1 New Oxford Street, London", new Date(28-02-1976), "760228/9246");
        BookTo bookTo = new BookTo("Android", "1234-4569-874", "Mobil", new Date(), "Jaryn");
        bookTo.setId(1l);
        ImpressionTO impressionTo = new ImpressionTO(123l,bookTo, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        LoanTO loanTo = new LoanTO(customerTo, impressionTo, new Date(123456), new Date(654321), DamageType.NEW);
        Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
        assertEquals(loan.getId(),loanTo.getId());
        assertEquals(loan.getDamageType(),loanTo.getDamageType());
        assertEquals(loan.getFromDate(),loanTo.getFromDate());
        assertEquals(loan.getToDate(),loanTo.getToDate());
        assertEquals(loan.getImpression().getId(),loanTo.getImpressionTo().getId());
        assertEquals(loan.getCustomer().getId(),loanTo.getCustomerTo().getId());
    } 
    
}
