package com.example.vnpay_demo1.service;

import com.example.vnpay_demo1.config.RabbitMQConfig;
import com.example.vnpay_demo1.config.RedisConfig;
import com.example.vnpay_demo1.dto.request.PaymentRequest;
import com.example.vnpay_demo1.dto.response.ApiResponse;
import com.example.vnpay_demo1.dto.response.PaymentResponse;
import com.example.vnpay_demo1.exception.ErrorCode;
import com.example.vnpay_demo1.mapper.PaymentMapper;
import com.example.vnpay_demo1.repository.PaymentRespository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Random;
import com.google.gson.Gson;


import static com.example.vnpay_demo1.util.CheckSumUtil.calculateResponseCheckSum;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {

    ValidationService validationService;
    BankService bankService;
    PaymentRespository paymentRespository;
    PaymentMapper paymentMapper;
    RedisCacheService redisCacheService;
    RedisConfig redisConfig;
    RabbitTemplate rabbitTemplate;

    // Tạo responseId
    String generateResponseId() {
        Random random = new Random();
        return String.valueOf(100000000 + random.nextInt(900000000));
    }

    // Tạo responseTime
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    // Lưu data request vào trong redis
    public PaymentResponse create(PaymentRequest request){
        PaymentResponse validationResult = validationService.validateRequest(request);

        String responseId = generateResponseId();
        String responseTime = getCurrentTime();
        String privateKey = bankService.getPrivateKey(request.getBankCode());
        String checkSum = calculateResponseCheckSum(validationResult.getCode(), validationResult.getMessage(), responseId, responseTime, privateKey);


        if (ErrorCode.SUCCESS.getCode().equals(validationResult.getCode())){
            // Lưu data vào bên trong mysql
            paymentRespository.save(paymentMapper.toPayment(request));
            // Xóa data khỏi cache
            redisCacheService.deleteOneFieldInHash(request.getBankCode(), (Object) request.getTokenKey());
        }
        return PaymentResponse.builder()
                .code(validationResult.getCode())
                .message(validationResult.getMessage())
                .responseId(responseId)
                .responseTime(responseTime)
                .checkSum(checkSum)
                .build();
    }

    // Tạo RabbitMQProducer
    private void rabbitMQProducer(PaymentRequest payment) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, payment);
            log.info("Sent PaymentRequest to RabbitMQ: {}", payment);
        } catch (Exception e) {
            log.error("Error sending to RabbitMQ: {}", e.getMessage());
        }
    }

    // Tạo RabbitMQConsumer
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @SendTo()
    private String rabbitMQConsumer(PaymentRequest request) {
        try {
            Gson gson = new Gson();
            String jsonData = gson.toJson(request);

            // Lưu vào Redis hash
            redisConfig.redisTemplate().opsForHash().put(request.getBankCode(), request.getTokenKey(), jsonData);
            redisConfig.redisTemplate().expire(request.getBankCode(), Duration.ofMinutes(30)); // Expire sau 30 phút

            log.info("Received and stored in Redis: {}", jsonData);
            return jsonData;
        } catch (Exception e) {
            log.error("Error in RabbitMQ consumer: {}", e.getMessage());
            return Collections.emptyMap().toString();
        }

    }

    public ApiResponse<Object> getOnePayment(String bankcode, String tokenKey){
        Gson gson = new Gson();
        var resultRedis = redisConfig.redisTemplate().opsForHash().get(bankcode, tokenKey);
        log.info("resultRedis_1 : {}",resultRedis);

        if (resultRedis == null){
            PaymentRequest result = paymentMapper.toPaymentRequest(paymentRespository.findByBankCodeAndTokenKey(bankcode, tokenKey));
            log.info("result:{}", result);
            if (result == null) {
//                throw new RuntimeException("Payment not found in DB");
                return ApiResponse.builder()
                        .code(ErrorCode.DATA_NOT_FOUND.getCode())
                        .message(ErrorCode.DATA_NOT_FOUND.getMessage())
                        .result(Collections.emptyMap())
                        .build();
            }

            // Gửi qua RabbitMQ producer
            rabbitMQProducer(result);
            //
            rabbitMQConsumer(result);
            return ApiResponse.builder()
                    .code(ErrorCode.SUCCESS.getCode())
                    .message(ErrorCode.SUCCESS.getMessage())
                    .result(gson.fromJson(rabbitMQConsumer(result), PaymentRequest.class))
                    .build();
        } else {
            return ApiResponse.builder()
                    .code(ErrorCode.SUCCESS.getCode())
                    .message(ErrorCode.SUCCESS.getMessage())
                    .result(resultRedis)
                    .build();
        }

    }
}
