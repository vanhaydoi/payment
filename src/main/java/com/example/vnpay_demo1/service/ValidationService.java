package com.example.vnpay_demo1.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.vnpay_demo1.config.RedisConfig;
import com.example.vnpay_demo1.dto.request.PaymentRequest;
import com.example.vnpay_demo1.dto.response.PaymentResponse;
import com.example.vnpay_demo1.exception.ErrorCode;
import com.example.vnpay_demo1.mapper.PaymentMapper;
import com.example.vnpay_demo1.repository.PaymentRespository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;



import static com.example.vnpay_demo1.util.CheckSumUtil.calculateRequestCheckSum;

@Builder
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class ValidationService {

    BankService bankService;
    RedisConfig redisConfig;



    public PaymentResponse validateRequest(PaymentRequest request){

        if (StringUtils.isBlank(request.getTokenKey())){
            log.info("TokenKey is null or empty");
            return PaymentResponse.builder()
                        .code(ErrorCode.INVALID.getCode())
                        .message(ErrorCode.INVALID.getMessage())
                        .build();
        }

        if (StringUtils.isBlank(request.getApiID())){
            log.info("ApiID is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getMobile())){
            log.info("Mobile is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getBankCode())){
            log.info("BankCode is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getAccountNo())){
            log.info("AccountNo is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getPayDate())){
            log.info("PayDate is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getAdditionalData())){
            log.info("AdditionalData is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (request.getDebitAmount() == null){
            log.info("DebitAmount is null ");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getRespCode())){
            log.info("RespCode is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getRespDesc())){
            log.info("RespDesc is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getTraceTransfer())){
            log.info("TraceTransfer is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getMessageType())){
            log.info("MessageType is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getCheckSum())){
            log.info("CheckSum is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getOrderCode())){
            log.info("OrderCode is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getUserName())){
            log.info("UserName is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getRealAmount())){
            log.info("RealAmount is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        if (StringUtils.isBlank(request.getPromotionCode())){
            log.info("PromotionCode is null or empty");
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID.getCode())
                    .message(ErrorCode.INVALID.getMessage())
                    .build();
        }

        request.setCheckSum(calculateRequestCheckSum(request.getMobile(), request.getBankCode(), request.getAccountNo(),
                request.getPayDate(), request.getDebitAmount(), request.getRespCode(),
                request.getTraceTransfer(), request.getMessageType(), bankService.getPrivateKey(request.getBankCode())));

        if (!request.getCheckSum().equals(calculateRequestCheckSum(request.getMobile(), request.getBankCode(), request.getAccountNo(),
                                                            request.getPayDate(), request.getDebitAmount(), request.getRespCode(),
                                                            request.getTraceTransfer(), request.getMessageType(), bankService.getPrivateKey(request.getBankCode())))){
            return PaymentResponse.builder()
                    .code(ErrorCode.INVALID_CHECKSUM.getCode())
                    .message(ErrorCode.INVALID_CHECKSUM.getMessage())
                    .build();
        }

        return PaymentResponse.builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }


}
