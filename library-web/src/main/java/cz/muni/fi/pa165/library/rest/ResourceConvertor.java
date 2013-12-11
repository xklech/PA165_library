/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.library.rest;

import cz.muni.fi.pa165.library.to.CustomerTo;



public class ResourceConvertor {
    
    
    public static CustomerTo fromCustomerResource(CustomerResource resource){
        if(resource == null){
            return null;
        }
        CustomerTo customerTo = new CustomerTo();
        customerTo.setAddress(resource.getAddress());
        customerTo.setDateOfBirth(resource.getDateOfBirth());
        customerTo.setFirstName(resource.getFirstName());
        customerTo.setLastName(resource.getLastName());
        customerTo.setId(resource.getId());
        customerTo.setPid(resource.getPid());
        
        
        return customerTo;
    }
    
    public static CustomerResource fromCustomerTo(CustomerTo customerTo){
        if(customerTo == null){
            return null;
        }
        CustomerResource customerResource = new CustomerResource();
        customerResource.setAddress(customerTo.getAddress());
        customerResource.setDateOfBirth(customerTo.getDateOfBirth());
        customerResource.setFirstName(customerTo.getFirstName());
        customerResource.setLastName(customerTo.getLastName());
        customerResource.setId(customerTo.getId());
        customerResource.setPid(customerTo.getPid());
        
        return customerResource;
    }
    
    
}
