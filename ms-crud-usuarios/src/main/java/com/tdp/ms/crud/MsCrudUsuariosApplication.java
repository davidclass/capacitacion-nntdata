package com.tdp.ms.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsCrudUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCrudUsuariosApplication.class, args);
	}

}
