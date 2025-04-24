package com.myproject.sm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// //disable security
// @SpringBootApplication(exclude = {
// 		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
// })

@SpringBootApplication
public class SmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmApplication.class, args);
	}

}
