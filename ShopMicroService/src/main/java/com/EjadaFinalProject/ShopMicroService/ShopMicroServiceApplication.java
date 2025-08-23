package com.EjadaFinalProject.ShopMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ShopMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopMicroServiceApplication.class, args);
	}

}
