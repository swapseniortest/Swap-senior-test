package com.swap.issues.recovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = {"com.swap.issues.recovery"})
public class IssuesRecoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuesRecoveryApplication.class, args);
	}

}
