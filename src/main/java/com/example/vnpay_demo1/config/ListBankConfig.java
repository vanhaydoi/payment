package com.example.vnpay_demo1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "")
@Data
public class ListBankConfig {
    private List<BankConfig> banks;

    // Phương thức tiện ích để tìm bank theo code
    public BankConfig findBankByCode(String bankCode) {
        return banks.stream()
                .filter(bank -> bank.getBankCode().equals(bankCode))
                .findFirst()
                .orElse(null);
    }
}
