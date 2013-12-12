
package cz.muni.fi.pa165.library.rest;

import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlRootElement
public class CustomerResource {
    private Long id;

    private String firstName; 

    private String lastName;  

    private String address;   
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date dateOfBirth;   

    private String pid;

    public CustomerResource() {}

    public CustomerResource(String firstName, String lastName, String address, Date dateOfBirth, String pid) {
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
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPlain() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "CustomerResource{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", pid=" + pid + '}';
    }
    
}
