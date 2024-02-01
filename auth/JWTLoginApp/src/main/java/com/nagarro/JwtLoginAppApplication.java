package com.nagarro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.nagarro")
public class JwtLoginAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtLoginAppApplication.class, args);
	}

}
