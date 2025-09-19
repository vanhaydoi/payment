package com.example.vnpay_demo1.controller;

import com.example.vnpay_demo1.dto.request.PaymentRequest;
import com.example.vnpay_demo1.dto.response.PaymentResponse;
import com.example.vnpay_demo1.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/vnpay")
public class PaymentController {

    PaymentService paymentService;

    @PostMapping("/payment")
    PaymentResponse create(@RequestBody PaymentRequest request){
        return paymentService.create(request);
    }

    @GetMapping("/payment/{bankcode}")
    Object getOnePayment(@PathVariable("bankcode") String bankcode,
                                 @RequestParam(value = "tokenKey", required = false) String tokenKey){
        return paymentService.getOnePayment(bankcode, tokenKey);
    }
}
