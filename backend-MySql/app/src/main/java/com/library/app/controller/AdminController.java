package com.library.app.controller;

import com.library.app.exception.BookException;
import com.library.app.exception.MessageException;
import com.library.app.requestmodels.AddBookRequest;
import com.library.app.service.AdminService;
import com.library.app.utils.ExtractJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/secure/add/book")
    public void addBook(@RequestHeader(value = "Authorization") String token, @RequestBody AddBookRequest addbookRequest) throws BookException {
        String admin = ExtractJwt.payloadJwtExtraction(token, "\"userType\"");
        String adminEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(admin == null || !admin.equals("admin")) {
            throw new BookException("Administration page only");
        }
        adminService.addBook(addbookRequest);
    }

    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws BookException {
        String admin = ExtractJwt.payloadJwtExtraction(token, "\"userType\"");
        String adminEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(admin == null || !admin.equals("admin")) {
            throw new BookException("Administration page only");
        }
        adminService.increaseBookQuantity(bookId);
    }

    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws BookException {
        String admin = ExtractJwt.payloadJwtExtraction(token, "\"userType\"");
        String adminEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(admin == null || !admin.equals("admin")) {
            throw new BookException("Administration page only");
        }
        adminService.decreaseBookQuantity(bookId);
    }

    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws BookException {
        String admin = ExtractJwt.payloadJwtExtraction(token, "\"userType\"");
        String adminEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(admin == null || !admin.equals("admin")) {
            throw new BookException("Administration page only");
        }
        adminService.deleteBook(bookId);
    }

}
