package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.service.CustomerService;
import cz.muni.fi.pa165.library.to.CustomerTo;
import java.util.Arrays;
import java.util.Collection;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UrlBinding("/customers/{$event}/{customer.id}")
public class CustomersActionBean extends BaseActionBean implements ValidationErrorHandler {

    static final Logger log = LoggerFactory.getLogger(BooksActionBean.class);

    @SpringBean
    protected CustomerService customerService;
    
    private boolean validationError;

    @ValidateNestedProperties(value = {
            @Validate(on = {"add","save"}, field = "firstName", required = true),
            @Validate(on = {"add","save"}, field = "lastName", required = true),
            @Validate(on = {"add","save"}, field = "address", required = true),
            @Validate(on = {"add","save"}, field = "dateOfBirth", required = true),
            @Validate(on = {"add","save"}, field = "pid", required = true)
    })
    private CustomerTo customer;
    
    private Collection<CustomerTo> customers;

    @Validate(on = {"findById"}, required = true, minvalue = 0)
    private Long findId;
    @Validate(on = {"findByName"}, required = true, minlength = 1)
    private String findFirstName;
    @Validate(on = {"findByName"}, required = true, minlength = 1)
    private String findLastName;

    public CustomerTo getCustomer() {
        return customer;
    }
    public void setCustomer(CustomerTo customer) {
        this.customer = customer;
    }
    public Collection<CustomerTo> getCustomers() {
        return customers;
    }
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        log.debug("errors() {}", errors);
        if ("add".equals(getContext().getEventName())) {
            log.debug("add() validationError =true ");
            validationError = true;
        }
        //return null to let the event handling continue
        return null;
    }
    
    @DefaultHandler
    public Resolution defaultHandler() {
        return new ForwardResolution("/customers.jsp");
    }

    public Resolution edit() {
        log.debug("edit customer={}", customer);
        if (customer == null || customer.getId() == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("customer.list.missing", customer.getId()));
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/customer/edit.jsp");
    }

    public Resolution add() {
        log.debug("add() customer={}", customer);
	try {
	    customerService.addCustomer(customer);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
        getContext().getMessages().add(new LocalizableMessage("customer.add.message",escapeHTML(customer.getFirstName()),escapeHTML(customer.getLastName()),customer.getId()));
        return new RedirectResolution(this.getClass());
    }
  
    public Resolution save() {
        log.debug("save() customer={}", customer);
	try {
	    customerService.updateCustomer(customer);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
        getContext().getMessages().add(new LocalizableMessage("customer.save.message",escapeHTML(customer.getFirstName()),escapeHTML(customer.getLastName()),customer.getId()));
        return new RedirectResolution(this.getClass());
    }
    
    public Resolution delete() {
        log.debug("delete() book={}", customer);
        try {
            customer = customerService.findCustomerById(customer.getId());
            customerService.deleteCustomer(customer);
            getContext().getMessages().add(new LocalizableMessage("customer.delete.message",escapeHTML(customer.getFirstName()),escapeHTML(customer.getLastName()),customer.getId()));
        } catch(Exception ex) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", customer.getId()));
        }
        return new RedirectResolution(this.getClass());
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"delete","edit"})
    public void loadCustomerFromDatabase() {
        log.debug("before delete, edit");
        String idStr = getContext().getRequest().getParameter("customer.id");
        if (idStr == null) {
            return;
        }
	Long id;
        try {
	    id = Long.parseLong(idStr);
        } catch(NumberFormatException ex) {
            log.debug("loadCustomerFromDatabase() number format exception - input:{}",idStr);
	    return;
        }
	try {
	    customer = customerService.findCustomerById(id);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
    }

    public Resolution findById() {
        log.debug("findById() ");
	CustomerTo customerTo = null;
	try {
	    customerTo = customerService.findCustomerById(findId);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
        if (customerTo != null) {
            customers = Arrays.asList(customerTo);
        } else {
            getContext().getMessages().add(new LocalizableError("customer.findId.message",findId));  
            return getContext().getSourcePageResolution();
        }
        return new ForwardResolution("/customer/list.jsp");                  
    }

    public Resolution findByName() {
        log.debug("findByName(" + findFirstName + ", " + findLastName + ") ");
	try {
	    customers = customerService.findCustomerByName(findFirstName, findLastName);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
        if (customers == null || customers.isEmpty()) {
            getContext().getMessages().add(new LocalizableError("customer.findName.message", findFirstName, findLastName));  
            return getContext().getSourcePageResolution();
        }
        return new ForwardResolution("/customer/list.jsp");                  
    }
    
    public boolean isValidationError() {
        return validationError;
    }

    public void setValidationError(boolean validationError) {
        this.validationError = validationError;
    }

    public Long getFindId() {
        return findId;
    }

    public void setFindId(Long findId) {
        this.findId = findId;
    }

    public String getFindFirstName() {
        return findFirstName;
    }

    public void setFindFirstName(String findFirstName) {
        this.findFirstName = findFirstName;
    }

    public String getFindLastName() {
        return findLastName;
    }

    public void setFindLastName(String findLastName) {
        this.findLastName = findLastName;
    }

}