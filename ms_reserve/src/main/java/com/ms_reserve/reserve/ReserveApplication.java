package com.ms_reserve.reserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReserveApplication.class, args);
	}
		// ms clients communication
		// database persistence
		// validate the requests and responses, add exception handling
}
