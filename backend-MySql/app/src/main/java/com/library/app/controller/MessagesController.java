package com.library.app.controller;

import com.library.app.entity.Message;
import com.library.app.exception.MessageException;
import com.library.app.exception.ReviewException;
import com.library.app.requestmodels.AdminQuestionRequest;
import com.library.app.service.MessagesService;
import com.library.app.utils.ExtractJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String token, @RequestBody Message messageRequest) throws MessageException {
        String userEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(userEmail == null) {
            throw new MessageException("User email is missing");
        }
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void updateMessage(@RequestHeader(value = "Authorization") String token, @RequestBody AdminQuestionRequest adminQuestionRequest) throws MessageException {
        String adminEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        String admin = ExtractJwt.payloadJwtExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")) {
            throw new MessageException("Administration page only");
        }
        messagesService.updateMessage(adminQuestionRequest, adminEmail);
    }
}
