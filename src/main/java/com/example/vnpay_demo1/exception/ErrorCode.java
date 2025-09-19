package com.example.vnpay_demo1.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter

public enum ErrorCode {
    SUCCESS("00", "Success"),
    INVALID("01", "Invalid request - required field is missing"),
    BANK_CODE("02", "Bank code not found"),
    INVALID_CHECKSUM("03", "Invalid checksum"),
    UNKNOW_ERROR("04", "Unknow error"),
    TOKEN_KEY_DUPLICATED("05", "Token key is duplicated"),
    SYSTEM_UNDER_MAINTENANCE("96", "System under maintenance"),
    SUSPICIOUS_TRANSACTION("08", "Suspicious transaction"),
    DATA_NOT_FOUND("09", "Data not found")
    ;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

}
