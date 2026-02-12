package com.javamoon.spring_algaworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javamoon.spring_algaworks.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class SpringAlgaworksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAlgaworksApplication.class, args);
	}

}
