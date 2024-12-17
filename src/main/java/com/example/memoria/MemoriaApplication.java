package com.example.memoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MemoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoriaApplication.class, args);
	}

}
