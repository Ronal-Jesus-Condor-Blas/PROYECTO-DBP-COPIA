package com.proyecto_dbp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProyectoDbpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoDbpApplication.class, args);
	}
}