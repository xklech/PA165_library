/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.CustomerService;
import cz.muni.fi.pa165.library.service.ImpressionService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michal Sukupčák
 */
@UrlBinding("/loans/{$event}/{$loan.id}")
public class LoansActionBean extends BaseActionBean implements ValidationErrorHandler {

    /* ---------------------------------------------------------------------- */
    /* DATA AND VARIABLES */
        
    /* ------------------------------ */
    /* Logger */
    
    final static Logger log = LoggerFactory.getLogger(LoansActionBean.class);   
    
    /* ------------------------------ */
    /* Class attributes */
    
    private CustomerTo customerTo;
    private BookTo bookTo;
    private ImpressionTo impressionTo;
    
    /* ------------------------------ */
    /* Spring beans */
    
    @SpringBean
    protected LoanService loanService;
    
    @SpringBean
    protected CustomerService customerService;
    
    @SpringBean
    protected ImpressionService impressionService;
    
    @SpringBean
    protected BookService bookService;
    
    /* ------------------------------ */
    /* Validation errors & error handler */
    
    private Boolean validationError;
    
    public Boolean isValidationError() {
	return this.validationError;
    }
    
    public void setValidationError (boolean validationError) {
	this.validationError = validationError;
    }
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        log.debug("errors() {}",errors);
        return null;
    }  
    
    /* ------------------------------ */
    /* Loan */
    
    private LoanTo loan;

    public LoanTo getLoan() {
	return this.loan;
    }

    public void setLoan(LoanTo loan) {
	this.loan = loan;
    }
    
    /* ------------------------------ */
    /* Loan collection */
    
    private List<LoanTo> loans;

    public List<LoanTo> getLoans() {
	return this.loans;
    }
    
    /* ------------------------------ */
    /* Customer collection */
    
    private List<CustomerTo> customers;

    public List<CustomerTo> getCustomers() {
	return this.customers;
    }
    
    /* ------------------------------ */
    /* Impression collection */
    
    private List<ImpressionTo> impressions;

    public List<ImpressionTo> getImpressions() {
	return this.impressions;
    }
    
    /* ------------------------------ */
    /* Book collection */
    
    private List<BookTo> books;

    public List<BookTo> getBooks() {
	return this.books;
    }
    
    /* ---------------------------------------------------------------------- */
    /* POST FIELDS */
    
    /* ------------------------------ */
    /* Find loan by id */
    
    @Validate(on = "findById", required = true, minvalue = 0)
    private Long findId;

    public Long getFindId() {
	return this.findId;
    }

    public void setFindId(Long findId) {
	this.findId = findId;
    }
    
    /* ------------------------------ */
    /* Find loans by customer */
    
    @Validate(on = "findByCustomer", required = true, minvalue = 0)
    private Long findCustomer;

    public Long getFindCustomer() {
	return this.findCustomer;
    }

    public void setFindCustomer(Long findCustomer) {
	this.findCustomer = findCustomer;
    }
    
    /* ------------------------------ */
    /* Find loans by from date */
    
    @Validate(on = "findByFromTo")
    private Date findFrom;

    public Date getFindFrom() {
	return this.findFrom;
    }

    public void setFindFrom(Date findFrom) {
	this.findFrom = findFrom;
    }
    
    /* ------------------------------ */
    /* Find loans by to date */
    @Validate(on = "findByFromTo")
    private Date findTo;

    public Date getFindTo() {
	return this.findTo;
    }

    public void setFindTo(Date findTo) {
	this.findTo = findTo;
    }
    
    /* ------------------------------ */
    /* Customer id */
    
    @Validate(on = {"prepare","add"}, required = true, minvalue = 1)
    private Long customerId;
    
    public void setCustomerId(Long customerId) {
	this.customerId = customerId;
    }
    
    public Long getCustomerId() {
	return this.customerId;
    }
    
    /* ------------------------------ */
    /* Book id */
    
    @Validate(on = {"prepare","add"}, required = true, minvalue = 1)
    private Long bookId;
    
    public void setBookId(Long bookId) {
	this.bookId = bookId;
    }
    
    public Long getBookId() {
	return this.bookId;
    }
    
    /* ------------------------------ */
    /* Impression id */
    
    @Validate(on = "add", required = true, minvalue = 1)
    private Long impressionId;
    
    public void setImpressionId(Long impressionId) {
	this.impressionId = customerId;
    }
    
    public Long getImpressionId() {
	return this.impressionId;
    }
    
    /* ---------------------------------------------------------------------- */
    /* INITIALIZATION */
    
    @Before(stages = LifecycleStage.BindingAndValidation)
    public void loadLists() {
	/*this.customerService.addCustomer(new CustomerTo(null,"John","Bon Jovi","ffdp",new Date(),"123456789"));
	this.bookService.save(new BookTo("Bible","123456789012","Fiction",new Date(),"Jesus & God"));*/
	this.customers = new ArrayList(this.customerService.findAllCustomers());
	this.books = this.bookService.findAllBooks();
	this.impressions = this.impressionService.findImpressionsByStatus(StatusType.AVAILIBLE);
    }
    
    /* ---------------------------------------------------------------------- */
    /* VALIDATION METHODS */
    
    @ValidationMethod(on = "findByCustomer")
    public void validateFindByCustomer() {
	try {
	    this.customerTo = this.customerService.findCustomerById(this.findCustomer);
	} catch (ServiceDataAccessException ex) {
	    getContext().getValidationErrors().add("findCustomer",new LocalizableError("loans.customerId.invalid",this.findCustomer));
	}
    }

    @ValidationMethod(on = {"prepare","add"})
    public void validateCustomerId() {
	try {
	    this.customerTo = this.customerService.findCustomerById(this.customerId);
	} catch (ServiceDataAccessException ex) {
	    getContext().getValidationErrors().add("customerId",new LocalizableError("loans.customerId.invalid",this.customerId));
	}
    }
    
    @ValidationMethod(on = {"prepare","add"})
    public void validateBookId() {
	try {
	    this.bookTo = this.bookService.findBookById(this.bookId);
	} catch (ServiceDataAccessException ex) {
	    getContext().getValidationErrors().add("bookId",new LocalizableError("loans.bookId.invalid",this.bookId));
	}
    }
    
    @ValidationMethod(on = "add")
    public void validateImpressionId() {
	try {
	    this.impressionTo = this.impressionService.findImpressionById(this.impressionId);
	} catch (ServiceDataAccessException ex) {
	    getContext().getValidationErrors().add("impressionId",new LocalizableError("loans.impressionId.invalid",this.impressionId));
	}
    }
    
    /* ---------------------------------------------------------------------- */
    /* ACTION HANDLERS */
    
    @DefaultHandler
    public Resolution defaultHandler() {
	log.debug("defaultHandler()");
        return new ForwardResolution("/loans.jsp");
    }
    
    public Resolution findById() {
	this.loans = Arrays.asList(this.loanService.findLoanById(this.findId));
	log.debug("findById()",this.loans);
	if (this.loans.get(0) == null) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findById.empty",this.findId));  
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByAllActive() {
	this.loans = this.loanService.findAllActiveLoans();
	log.debug("findAllActive()",this.loans);
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findAllActive.empty"));  
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByCustomer() {
	this.loans = this.loanService.findLoansByCustomer(this.customerTo);
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findByCustomer.empty",this.findCustomer));
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByFromTo() {
	throw new UnsupportedOperationException("findByFromTo");
    }
    
    public Resolution prepare() {
	return new ForwardResolution("/loan/add.jsp");
    }
    
    public Resolution add() {
	this.loan = new LoanTo(this.customerTo,this.impressionTo,new Date(),null,DamageType.NEW);
	this.loanService.addLoan(this.loan);
	this.impressionTo.setStatus(StatusType.LOANED);
	this.impressionService.updateImpression(this.impressionTo);
	getContext().getMessages().add(new LocalizableMessage("loans.add.confirm",escapeHTML(this.customerTo.getFirstName()),escapeHTML(this.customerTo.getLastName()),escapeHTML(this.bookTo.getAuthor()),escapeHTML(this.bookTo.getName())));
	return new RedirectResolution(this.getClass());
    }
    
    public Resolution edit() {
	throw new UnsupportedOperationException("edit");
    }
    
    public Resolution delete() {
	throw new UnsupportedOperationException("delete");
    }
    
}
