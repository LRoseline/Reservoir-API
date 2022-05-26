package com.tfriends.reservoir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.tfriends.*"})
@EnableScheduling
public class ReservoirApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservoirApplication.class, args);
	}

}
