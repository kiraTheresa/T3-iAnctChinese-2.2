package com.zjgsu.kirateresa.BiograFi_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BiograFiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiograFiBackendApplication.class, args);
	}

}
