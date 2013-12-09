/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.library.exceptions;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author Jaroslav Klech 
 */
public class ServiceDataAccessException extends DataAccessException {

    public ServiceDataAccessException(String msg) {
        super(msg);
    }

    public ServiceDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
