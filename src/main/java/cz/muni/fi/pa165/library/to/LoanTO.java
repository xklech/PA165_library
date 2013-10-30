package cz.muni.fi.pa165.library.to;

import cz.muni.fi.pa165.library.enums.DamageType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Transfer object for entity Loan
 *
 * @author Michal Sukupčák
 */
public class LoanTO implements Serializable {
    
    private Long id;
    
    private CustomerTO customer;
    
    private ImpressionTO impression;
    
    private Date fromDate;
    
    private Date toDate;
    
    private DamageType damageType;

    public LoanTO() {}
    
    public LoanTO(CustomerTO customer, ImpressionTO impression, Date fromDate, Date toDate, DamageType damageType) {
	this.customer = customer;
	this.impression = impression;
	this.fromDate = fromDate;
	this.toDate = toDate;
	this.damageType = damageType;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    public CustomerTO getCustomer() {
	return this.customer;
    }

    public void setCustomer(CustomerTO customer) {
	this.customer = customer;
    }

    public ImpressionTO getImpression() {
	return this.impression;
    }

    public void setImpression(ImpressionTO impression) {
	this.impression = impression;
    }
    
    public Date getFromDate() {
	return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }
    
    public Date getToDate() {
	return this.toDate;
    }

    public void setToDate(Date toDate) {
	this.toDate = toDate;
    }
    
    public DamageType getDamageType() {
	return this.damageType;
    }

    public void setDamageType(DamageType damageType) {
	this.damageType = damageType;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 67 * hash + Objects.hashCode(this.id);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final LoanTO other = (LoanTO) obj;
	return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
	return "Loan{" + "id=" + id + ", customer=" + customer + ", impression=" + impression + ", fromDate=" + fromDate + ", toDate=" + toDate + ", damageType=" + damageType + '}';
    }
    
}
