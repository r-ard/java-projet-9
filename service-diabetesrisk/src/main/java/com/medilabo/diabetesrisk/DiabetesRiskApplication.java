package com.medilabo.diabetesrisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.diabetesrisk")
public class DiabetesRiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiabetesRiskApplication.class, args);
	}

}
