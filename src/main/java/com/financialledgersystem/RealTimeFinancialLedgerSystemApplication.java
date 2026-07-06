package com.financialledgersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RealTimeFinancialLedgerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealTimeFinancialLedgerSystemApplication.class, args);
	}

}
