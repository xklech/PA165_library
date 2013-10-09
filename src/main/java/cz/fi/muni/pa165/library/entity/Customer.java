package cz.fi.muni.pa165.library.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Ivana Haraslínová
 */
@Entity
public class Customer implements Serializable{
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
