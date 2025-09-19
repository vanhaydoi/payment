package com.example.vnpay_demo1;


import com.example.vnpay_demo1.config.ListBankConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(ListBankConfig.class)
public class VnpayDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(VnpayDemo1Application.class, args);
	}

}
