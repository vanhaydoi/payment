package com.example.vnpay_demo1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class CheckSumUtil {
    private static final Logger logger = LoggerFactory.getLogger(CheckSumUtil.class);

    public static String calculateRequestCheckSum(String mobile, String bankCode, String accountNo,
                                                  String payDate, Long debitAmount, String respCode,
                                                  String traceTransfer, String messageType, String privateKey) {
        String data = mobile + bankCode + accountNo + payDate + debitAmount + respCode + traceTransfer + messageType + privateKey;
//        logger.debug("Calculating request checksum for data: {}", data.replaceAll(privateKey, "***"));
        return sha256Hash(data);
    }

    public static String calculateResponseCheckSum(String code, String message, String responseId,
                                                   String responseTime, String privateKey) {
        String data = code + message + responseId + responseTime + privateKey;
//        logger.debug("Calculating response checksum for data: {}", data.replaceAll(privateKey, "***"));
        return sha256Hash(data);
    }

    private static String sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            logger.error("Error calculating SHA256 hash", e);
            throw new RuntimeException("Error calculating checksum", e);
        }
    }
}
