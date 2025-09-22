package com.example.vnpay_demo1.service;

import com.example.vnpay_demo1.dto.request.PaymentRequest;
import com.example.vnpay_demo1.dto.response.ApiResponse;
import com.example.vnpay_demo1.dto.response.PaymentResponse;

public interface PaymentServiceImpl {
    ApiResponse<Object> getOnePayment(String bankcode, String tokenKey);
    PaymentResponse create(PaymentRequest request);
}
