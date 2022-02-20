package com.example.ibmmq.ibmmq_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class IbmmqTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbmmqTestApplication.class, args);
	}

}
