package com.bridgelabz.eurekaUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ToDoEurekaUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoEurekaUserServiceApplication.class, args);
	}
}
