package com.example.vnpay_demo1.config;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class BankConfig {
    private String bankCode;
    private String privateKey;
    private String ips;
}
