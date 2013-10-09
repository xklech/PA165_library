package cz.fi.muni.pa165.library.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Petr Vacek
 */
@Entity
public class Impression implements Serializable {
    @Id
    private Long id;
    
    @ManyToOne
    private Book book;

    
}
