package com.medilabo.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServicePatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePatientApplication.class, args);
	}

}
