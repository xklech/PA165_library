/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.pa165.library.service;


import cz.fi.muni.pa165.library.dao.BookDao;
import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.exceptions.BookDaoException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jarek
 */
@Service
public class BookServiceImpl {

    @Autowired
    private BookDao bookDao;
    
    
    @Transactional
    public void save(Book book){
            
        try {
            System.err.println("service:save called");
            bookDao.addBook(book);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public Book findBookById(Long id){
        Book book = null;
        try{
            book = bookDao.findBookById(id);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }
    
    public List<Book> findByName(String name){
        return (List<Book>) bookDao.findBooksByName(name);
    }
    
}
