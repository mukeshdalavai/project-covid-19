package com.covidMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class Application {
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
