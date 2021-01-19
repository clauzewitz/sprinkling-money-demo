package com.clauzewitz.sprinklingmoneydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SprinklingMoneyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprinklingMoneyDemoApplication.class, args);
	}

}
