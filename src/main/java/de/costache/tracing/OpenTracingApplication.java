package de.costache.tracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OpenTracingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenTracingApplication.class, args);
	}

}
