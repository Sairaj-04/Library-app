package com.library.app.controller;

import com.library.app.entity.Book;
import com.library.app.entity.Review;
import com.library.app.exception.BookException;
import com.library.app.exception.ReviewException;
import com.library.app.requestmodels.ReviewRequest;
import com.library.app.service.ReviewService;
import com.library.app.utils.ExtractJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws ReviewException {
        String userEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(userEmail == null) {
            throw new ReviewException("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }

    @GetMapping("/secure/user/book")
    public Boolean userReviewListed(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId) throws ReviewException {
        String userEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(userEmail == null) {
            throw new ReviewException("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }

}
