package com.abhishek.library.management.Controller;

import com.abhishek.library.management.DataAccessLayer.IssuedBooks;
import com.abhishek.library.management.DataAccessLayer.IssuedBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


//annotation was introduced in Spring 4.0 to simplify the creation of RESTful web services.
@RestController
public class IssuedBooksResource {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    //The @Autowired annotation in spring automatically injects the dependent beans into the associated references of a POJO class.
    @Autowired
    IssuedBooksRepository repository;


    @GetMapping("/issuedBooks")
    List<IssuedBooks> findAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/issueBook")
    @ResponseStatus(HttpStatus.CREATED)
    public IssuedBooks issueBook(@RequestBody IssuedBooks issuedBooks) throws Exception{ //
        try {
            return repository.save(issuedBooks);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    @GetMapping(value = "/searchIssuedBooksByUser")
    public List<IssuedBooks> searchIssuedBooksByUser(@RequestParam(value = "q") String userId){
        LOGGER.info("searchIssuedBooksByUser called with userId "+userId);
        List<IssuedBooks> books = repository.findAll();
        ArrayList<IssuedBooks> list = new ArrayList<IssuedBooks>();
        for(IssuedBooks book : books){
            int id = Integer.parseInt(userId);
            if(book.getUser_id()==id){
                list.add(book);
            }
        }
        return list;
    }

}
