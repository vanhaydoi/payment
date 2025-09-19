package com.example.vnpay_demo1.service;

import com.example.vnpay_demo1.config.RedisConfig;
import com.example.vnpay_demo1.dto.request.PaymentRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Builder
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class RedisCacheService {
    RedisConfig redisConfig;


    // Lấy tất cả fields trong hashkey
    public Set<Object> getAllFields(String hashKey){
        Set<Object> fields = redisConfig.redisTemplate().opsForHash().keys(hashKey);
        log.info("fields: {}", fields);
        return fields;
    }

    // Xóa nhiều field cụ thể trong hashKey
    public void deleteHashFields(String hashKey, String... fields){
        if (fields.length > 0){
            redisConfig.redisTemplate().opsForHash().delete(hashKey, (Object[]) fields);
        }
    }

    // Xóa tất cả fields trong hashKey
    public void deleteAllFieldsInHash(String hashKey){
        if (!getAllFields(hashKey).isEmpty()){
            redisConfig.redisTemplate().opsForHash().delete(hashKey, getAllFields(hashKey).toArray());
        }
    }

    // Xóa 1 filed trong hashKey

    public void deleteOneFieldInHash(String hashKey, Object field){
        if (field != null){
            redisConfig.redisTemplate().opsForHash().delete(hashKey, field);
        }
    }
}
