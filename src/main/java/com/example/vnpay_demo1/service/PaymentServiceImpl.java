package com.example.vnpay_demo1.service;

import com.example.vnpay_demo1.dto.response.ApiResponse;

public interface PaymentServiceImpl {
    ApiResponse<Object> getOnePayment(String bankcode, String tokenKey);
}
