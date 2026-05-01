package com.renerodriguez.core_client_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CoreClientManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreClientManagementSystemApplication.class, args);

	}

}
