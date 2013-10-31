/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.to.BookTo;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jaroslav Klech
 */
public class BookServiceTest extends AbstractIntegrationTest{
    @Autowired
    BookService bookService;
    
    
    @Test
    public void testSaveBook() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookService.save(bookTo);
        assertNotNull(bookTo.getId());
        
    }
}
