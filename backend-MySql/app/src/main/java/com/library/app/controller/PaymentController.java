package com.library.app.controller;

import com.library.app.exception.MessageException;
import com.library.app.exception.PaymentException;
import com.library.app.requestmodels.PaymentInfoRequest;
import com.library.app.service.PaymentService;
import com.library.app.utils.ExtractJwt;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws StripeException {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value = "Authorization") String token) throws PaymentException {
        String userEmail = ExtractJwt.payloadJwtExtraction(token, "\"sub\"");
        if(userEmail == null) {
            throw new PaymentException("User email is missing");
        }

        return paymentService.stripePayment(userEmail);
    }

}
