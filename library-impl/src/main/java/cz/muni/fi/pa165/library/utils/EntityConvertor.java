package cz.muni.fi.pa165.library.utils;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.to.LoanTo;

/**
 *
 * @author Jaroslav Klech, Michal Sukupčák, Ivana Haraslínová, Petr Vacek
 */
public class EntityConvertor {
    
    /**
     *  Converts Book into Book transfer object
     * @param book book to be converted
     * @return book transfer object
     */
    public static BookTo convertFromBook(Book book) {
        if (book == null) {
            return null;
        }
        BookTo bookTo = new BookTo();
        bookTo.setId(book.getId());
        bookTo.setName(book.getName());
        bookTo.setIsbn(book.getISBN());
        bookTo.setDepartment(book.getDepartment());
        bookTo.setPublishDate(book.getPublishDate());
        bookTo.setAuthor(book.getAuthor());
        return bookTo;
    }
    
    /**
     *  Converts Book transfer object into Book
     * @param bookTo bookTo to be converted
     * @return book
     */
    public static Book convertFromBookTo(BookTo bookTo) {
        if (bookTo == null) {
            return null;
        }
        Book book = new Book();
        book.setId(bookTo.getId());
        book.setName(bookTo.getName());
        book.setISBN(bookTo.getIsbn());
        book.setDepartment(bookTo.getDepartment());
        book.setPublishDate(bookTo.getPublishDate());
        book.setAuthor(bookTo.getAuthor());
        return book;
    }
    /**
     *  Converts Impression into impression transfer object
     * @param impr impression to be converted
     * @return impression transfer object
     */
    public static ImpressionTo convertFromImpression(Impression impr) {
        if (impr == null) {
            return null;
        }
        ImpressionTo impressionTo = new ImpressionTo();
        impressionTo.setId(impr.getId());
        impressionTo.setBookTo(EntityConvertor.convertFromBook(impr.getBook()));
        impressionTo.setInitialDamage(impr.getInitialDamage());
        impressionTo.setDamage(impr.getDamage());
        impressionTo.setStatus(impr.getStatus());
        return impressionTo;
    }

    /**
     * Converts Impression transfer object into Impression
     * @param imprTo impressionTo to be converted
     * @return Impression
     */
    public static Impression convertFromImpressionTo(ImpressionTo imprTo) {
        if (imprTo == null) {
            return null;
        }
        Impression impression = new Impression();
        impression.setId(imprTo.getId());
        impression.setBook(EntityConvertor.convertFromBookTo(imprTo.getBookTo()));
        impression.setInitialDamage(imprTo.getInitialDamage());
        impression.setDamage(imprTo.getDamage());
        impression.setStatus(imprTo.getStatus());
        return impression;
    }
    
    /**
     * Converts Customer into customer transfer object
     * @param customer customer to be converted
     * @return customer transfer object
     */
    public static CustomerTo convertFromCustomer(Customer customer){
        if (customer == null) {
            return null;
        }
        CustomerTo customerTo = new CustomerTo();
        customerTo.setId(customer.getId());
        customerTo.setFirstName(customer.getFirstName());
        customerTo.setLastName(customer.getLastName());
        customerTo.setAddress(customer.getAddress());
        customerTo.setDateOfBirth(customer.getDateOfBirth());
        customerTo.setPid(customer.getPid());
        return customerTo;
    }

    
    /**
     * Converts Customer transfer object into Customer
     * @param customerTo CustomerTo to be converted
     * @return Customer
     */
    public static Customer convertFromCustomerTo(CustomerTo customerTo) {
        if (customerTo == null) {
            return null;
        }
        Customer customer= new Customer();
        customer.setId(customerTo.getId());
        customer.setFirstName(customerTo.getFirstName());
        customer.setLastName(customerTo.getLastName());
        customer.setAddress(customerTo.getAddress());
        customer.setDateOfBirth(customerTo.getDateOfBirth());
        customer.setPid(customerTo.getPid());
        return customer;
    }

    
    /**
     * Converts Loan into loan transfer object
     * @param loan loan to be converted
     * @return loan transfer object
     */
    public static LoanTo convertFromLoan(Loan loan) {
	if (loan == null) {
	    return null;
	}
	LoanTo loanTo = new LoanTo();
	loanTo.setId(loan.getId());
	loanTo.setCustomerTo(EntityConvertor.convertFromCustomer(loan.getCustomer()));
	loanTo.setImpressionTo(EntityConvertor.convertFromImpression(loan.getImpression()));
	loanTo.setFromDate(loan.getFromDate());
	loanTo.setToDate(loan.getToDate());
	loanTo.setDamageType(loan.getDamageType());
        return loanTo;
    }

    /**
     * Converts Loan transfer object into Loan
     * @param loanTo LoanTo to be converted
     * @return Loan
     */
    public static Loan convertFromLoanTo(LoanTo loanTo) {
	if (loanTo == null) {
	    return null;
	}
        Loan loan = new Loan();
	loan.setId(loanTo.getId());
	loan.setCustomer(EntityConvertor.convertFromCustomerTo(loanTo.getCustomerTo()));
	loan.setImpression(EntityConvertor.convertFromImpressionTo(loanTo.getImpressionTo()));
	loan.setFromDate(loanTo.getFromDate());
	loan.setToDate(loanTo.getToDate());
	loan.setDamageType(loanTo.getDamageType());
        return loan;
    }
    
    
}
