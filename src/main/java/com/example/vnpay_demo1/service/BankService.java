package com.example.vnpay_demo1.service;

import com.example.vnpay_demo1.config.BankConfig;
import com.example.vnpay_demo1.config.ListBankConfig;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@Data
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankService {
    ListBankConfig listBankConfig;

    // Lấy tất cả bank
    public List<BankConfig> getAllBanks() {
        return listBankConfig.getBanks();
    }

    // Tìm bank theo code
    public BankConfig getBankByCode(String bankCode) {
        return listBankConfig.findBankByCode(bankCode);
    }

    // Lấy private key của bank
    public String getPrivateKey(String bankCode) {
        BankConfig bank = getBankByCode(bankCode);
        return bank != null ? bank.getPrivateKey() : null;
    }
}
