/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library;

import static cz.muni.fi.pa165.library.BaseActionBean.escapeHTML;
import cz.muni.fi.pa165.library.enums.StatusType;
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
    
    final static Logger log = LoggerFactory.getLogger(LoansActionBean.class);   

    private CustomerTo customerTo;
    private BookTo bookTo;
    private ImpressionTo impressionTo;
    private LoanTo loanTo;

    @SpringBean
    protected LoanService loanService;
    
    @SpringBean
    protected CustomerService customerService;
    
    @SpringBean
    protected ImpressionService impressionService;
    
    @SpringBean
    protected BookService bookService;

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

    private LoanTo loan;

    public LoanTo getLoan() {
	return this.loan;
    }

    public void setLoan(LoanTo loan) {
	this.loan = loan;
    }
  
    private List<LoanTo> loans;

    public List<LoanTo> getLoans() {
	return this.loans;
    }
    
    private List<CustomerTo> customers;

    public List<CustomerTo> getCustomers() {
	return this.customers;
    }

    private List<ImpressionTo> impressions;

    public List<ImpressionTo> getImpressions() {
	return this.impressions;
    }

    private List<BookTo> books;

    public List<BookTo> getBooks() {
	return this.books;
    }
    
    @Validate(on = "findById", required = true, minvalue = 0)
    private Long findId;

    public Long getFindId() {
	return this.findId;
    }

    public void setFindId(Long findId) {
	this.findId = findId;
    }
    
    @Validate(on = "findByCustomer", required = true, minvalue = 0)
    private Long findCustomer;

    public Long getFindCustomer() {
	return this.findCustomer;
    }

    public void setFindCustomer(Long findCustomer) {
	this.findCustomer = findCustomer;
    }

    @Validate(on = "findByFromTo")
    private Date findFrom;

    public Date getFindFrom() {
	return this.findFrom;
    }

    public void setFindFrom(Date findFrom) {
	this.findFrom = findFrom;
    }

    @Validate(on = "findByFromTo")
    private Date findTo;

    public Date getFindTo() {
	return this.findTo;
    }

    public void setFindTo(Date findTo) {
	this.findTo = findTo;
    }

    @Validate(on = {"prepare","add"}, required = true, minvalue = 1)
    private Long customerId;
    
    public void setCustomerId(Long customerId) {
	this.customerId = customerId;
    }
    
    public Long getCustomerId() {
	return this.customerId;
    }

    @Validate(on = {"prepare","add"}, required = true, minvalue = 1)
    private Long bookId;
    
    public void setBookId(Long bookId) {
	this.bookId = bookId;
    }
    
    public Long getBookId() {
	return this.bookId;
    }
    
    @Validate(on = "add", required = true, minvalue = 1)
    private Long impressionId;
    
    public void setImpressionId(Long impressionId) {
	this.impressionId = impressionId;
    }
    
    public Long getImpressionId() {
	return this.impressionId;
    }

    @Validate(on = "restore", required = true, minvalue = 1)
    private Long loanId;
    
    public void setLoanId(Long loanId) {
	this.loanId = loanId;
    }
    
    public Long getLoanId() {
	return this.loanId;
    }
        
    @Before(stages = LifecycleStage.BindingAndValidation)
    public void loadLists() {
	this.customers = new ArrayList(this.customerService.findAllCustomers());
	try {
	    this.books = this.bookService.findAllBooks();
	    this.impressions = this.impressionService.findImpressionsByStatus(StatusType.AVAILIBLE);
	} catch (Exception ex) {
	    log.error("service error",ex);
	}
    }
        
    @ValidationMethod(on = "findByCustomer")
    public void validateFindByCustomer() {
	try {
	    this.customerTo = this.customerService.findCustomerById(this.findCustomer);
	} catch (Exception ex) {
            log.error("service error",ex);
	    getContext().getValidationErrors().add("findCustomer",new LocalizableError("loans.customerId.invalid",this.findCustomer));
	}
    }

    @ValidationMethod(on = {"prepare","add"})
    public void validateCustomerId() {
	try {
	    this.customerTo = this.customerService.findCustomerById(this.customerId);
	} catch (Exception ex) {
            log.error("service error",ex);
	    getContext().getValidationErrors().add("customerId",new LocalizableError("loans.customerId.invalid",this.customerId));
	}
    }
    
    @ValidationMethod(on = {"prepare","add"})
    public void validateBookId() {
	try {
	    this.bookTo = this.bookService.findBookById(this.bookId);
	} catch (Exception ex) {
	    getContext().getValidationErrors().add("bookId",new LocalizableError("loans.bookId.invalid",this.bookId));
	}
    }
    
    @ValidationMethod(on = "add")
    public void validateImpressionId() {
	try {

	    this.impressionTo = this.impressionService.findImpressionById(this.impressionId);
	} catch (Exception ex) {
            log.error("service error",ex);
	    getContext().getValidationErrors().add("impressionId",new LocalizableError("loans.impressionId.invalid",this.impressionId));
	}
    }
    
    @ValidationMethod(on = {"restore","delete"})
    public void validateLoanId() {
	try {
	    this.loanTo = this.loanService.findLoanById(this.loanId);
	} catch (Exception ex) {
            log.error("service error",ex);
	    getContext().getValidationErrors().add("loanId",new LocalizableError("loans.loanId.invalid",this.loanId));
	}
    }
        
    @DefaultHandler
    public Resolution defaultHandler() {
	log.debug("defaultHandler()");
        return new ForwardResolution("/loans.jsp");
    }
    
    public Resolution findById() {
	try {
	    this.loans = Arrays.asList(this.loanService.findLoanById(this.findId));
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	log.debug("findById()",this.loans);
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findById.empty",this.findId));  
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByAllActive() {
	try {
	    this.loans = this.loanService.findAllActiveLoans();
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	log.debug("findAllActive()",this.loans);
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findAllActive.empty"));  
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByCustomer() {
	try {
	    this.loans = this.loanService.findLoansByCustomer(this.customerTo);
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findByCustomer.empty",this.findCustomer));
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByFromTo() {
	try {
	    this.loans = this.loanService.findLoansByFromTo(this.findFrom,this.findTo);
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findByFromTo.empty",this.findFrom,this.findTo));
	    return getContext().getSourcePageResolution();
	} else {
	    return new ForwardResolution("/loan/list.jsp");
	}
    }
    
    public Resolution prepare() {
	return new ForwardResolution("/loan/add.jsp");
    }
    
    public Resolution add() {
	this.loan = new LoanTo(this.customerTo,this.impressionTo,new Date(),null,this.impressionTo.getDamage());
	try {
	    this.loanService.addLoan(this.loan);
	    this.impressionTo.setStatus(StatusType.LOANED);
	    this.impressionService.updateImpression(this.impressionTo);
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	getContext().getMessages().add(new LocalizableMessage("loans.add.confirm",escapeHTML(this.customerTo.getFirstName()),escapeHTML(this.customerTo.getLastName()),escapeHTML(this.bookTo.getAuthor()),escapeHTML(this.bookTo.getName())));
	return new RedirectResolution(this.getClass());
    }
    
    public Resolution restore() {
	this.loanTo.setToDate(new Date());
	this.loanTo.getImpressionTo().setStatus(StatusType.AVAILIBLE);
	try {
	    this.loanService.updateLoan(this.loanTo);
	    this.impressionService.updateImpression(this.loanTo.getImpressionTo());
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	getContext().getMessages().add(new LocalizableMessage("loans.restore.confirm",escapeHTML(this.loanTo.getCustomerTo().getFirstName()),escapeHTML(this.loanTo.getCustomerTo().getLastName()),escapeHTML(this.loanTo.getImpressionTo().getBookTo().getAuthor()),escapeHTML(this.loanTo.getImpressionTo().getBookTo().getName())));
	return new RedirectResolution(this.getClass());	
    }
    
    public Resolution delete() {
	try {
	    this.loanService.deleteLoan(this.loanTo);
	} catch (Exception ex) {
	    log.error("service error",ex);
            getContext().getMessages().add(new LocalizableError("common.find.error"));  
            return getContext().getSourcePageResolution();
	}
	getContext().getMessages().add(new LocalizableMessage("loans.delete.confirm",this.loanTo.getId()));
	return new RedirectResolution(this.getClass());	
    }
    
}
