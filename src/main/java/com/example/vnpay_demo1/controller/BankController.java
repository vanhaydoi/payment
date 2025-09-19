package com.example.vnpay_demo1.controller;

import com.example.vnpay_demo1.config.BankConfig;
import com.example.vnpay_demo1.config.ListBankConfig;
import com.example.vnpay_demo1.service.BankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("banks")
public class BankController {

    BankService bankService;

    @GetMapping()
    public List<BankConfig> getAllBanks(){
        return bankService.getAllBanks();
    }
}
