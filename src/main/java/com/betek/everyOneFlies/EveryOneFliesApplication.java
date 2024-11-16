package com.betek.everyOneFlies;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EveryOneFliesApplication {

	public static void main(String[] args) {
		if (System.getenv("RAILWAY_ENVIRONMENT") == null) {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
			System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		}
		SpringApplication.run(EveryOneFliesApplication.class, args);
	}

}
