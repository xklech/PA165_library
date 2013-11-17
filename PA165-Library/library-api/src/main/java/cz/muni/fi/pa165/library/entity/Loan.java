package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.DamageType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Michal Sukupčák
 */
@Entity
public class Loan implements Serializable {
        
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Impression impression;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fromDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date toDate;
    
    private DamageType damageType;

    public Loan() {}
    
    public Loan (Customer customer, Impression impression, Date fromDate, Date toDate, DamageType damageType) {
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
        
    public Customer getCustomer() {
	return this.customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public Impression getImpression() {
	return this.impression;
    }

    public void setImpression(Impression impression) {
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
	final Loan other = (Loan) obj;
	if (!Objects.equals(this.id, other.id)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Loan{" + "id=" + id + ", customer=" + customer + ", impression=" + impression + ", fromDate=" + fromDate + ", toDate=" + toDate + ", damageType=" + damageType + '}';
    }

}
