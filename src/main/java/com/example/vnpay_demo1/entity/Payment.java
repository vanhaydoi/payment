package com.example.vnpay_demo1.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column
    String tokenKey;

    @Column
    String apiID;

    @Column
    String mobile;

    @Column
    String bankCode = "970445";
    @Column
    String accountNo;

    @Column
    String payDate;

    @Column
    String additionalData;

    @Column
    Long debitAmount;

    @Column
    String respCode;

    @Column
    String respDesc;

    @Column
    String traceTransfer;

    @Column
    String messageType;

    @Column
    String checkSum;

    @Column
    String orderCode;

    @Column
    String userName;

    @Column
    String realAmount;

    @Column
    String promotionCode;
}
