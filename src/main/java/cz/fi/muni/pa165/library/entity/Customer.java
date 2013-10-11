package cz.fi.muni.pa165.library.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Ivana Haraslínová
 */
@Entity
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Collection<Loan> loans;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Loan> getLoans() {
	return loans;
    }

    public void setLoans(Collection<Loan> loans) {
	this.loans = loans;
    }
     
}
