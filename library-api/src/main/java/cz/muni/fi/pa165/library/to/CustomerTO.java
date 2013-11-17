package cz.muni.fi.pa165.library.to;

import java.util.Date;
import java.util.Objects;

/**
 * Transfer object for entity Customer
 *
 * @author Ivana Haraslínová
 */
public class CustomerTO {
    private Long id;

    private String firstName; 

    private String lastName;  

    private String address;   

    private Date dateOfBirth;   

    private String pid;

    public CustomerTO() {
    }

    public CustomerTO(Long id, String firstName, String lastName, String address, Date dateOfBirth, String pid) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.pid = pid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final CustomerTO other = (CustomerTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CustomerTO{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", pid=" + pid + '}';
    }

    
}
