package com.example.vnpay_demo1.repository;

import com.example.vnpay_demo1.entity.Payment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRespository extends JpaRepository<Payment, String> {

    @Query("""
            select p from Payment p where p.bankCode = :bankCode and p.tokenKey = :tokenKey
            """)
    Payment findByBankCodeAndTokenKey(@Param("bankCode") String bankCode, @Param("tokenKey") String tokenKey);
}
