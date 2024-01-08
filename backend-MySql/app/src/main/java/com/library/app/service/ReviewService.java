package com.library.app.service;

import com.library.app.dao.ReviewRepository;
import com.library.app.entity.Review;
import com.library.app.exception.ReviewException;
import com.library.app.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;


@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws ReviewException {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());

        if(validateReview != null) {
            throw new ReviewException(("You have already given the review for this book."));
        }

        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating((reviewRequest.getRating()));
        review.setUserEmail(userEmail);
        if(reviewRequest.getReviewDescription().isPresent()) {
            review.setReview_Description((reviewRequest.getReviewDescription().get()));
            review.setReview_Description(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        if(validateReview != null) {
            return true;
        }
        return false;
    }
}
