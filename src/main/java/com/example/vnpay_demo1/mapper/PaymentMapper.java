package com.example.vnpay_demo1.mapper;

import com.example.vnpay_demo1.dto.request.PaymentRequest;
import com.example.vnpay_demo1.dto.response.PaymentResponse;
import com.example.vnpay_demo1.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentRequest request);

    PaymentRequest toPaymentRequest(Payment payment);

}
