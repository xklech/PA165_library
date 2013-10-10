package cz.fi.muni.pa165.library.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Ivana Haraslínová
 */
@Entity
public class Customer implements Serializable{
    @Id
    private Long id;

    @OneToMany
    private Collection<Loan> loans;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
