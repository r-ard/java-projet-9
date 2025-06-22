package com.medilabo.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.note")
public class ServiceNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceNoteApplication.class, args);
	}

}
