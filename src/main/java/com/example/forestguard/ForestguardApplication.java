package com.example.forestguard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForestguardApplication {
//	@Value("${port.number}")
//	private static int port;

	public static void main(String[] args) {
		SpringApplication.run(ForestguardApplication.class, args);
		System.out.println("Server is running ");
	}

}
