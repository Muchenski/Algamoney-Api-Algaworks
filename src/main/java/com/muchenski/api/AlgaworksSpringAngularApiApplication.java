package com.muchenski.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.muchenski.api.config.property.ApiProperty;

@EnableConfigurationProperties(ApiProperty.class)
@EnableScheduling
@SpringBootApplication
public class AlgaworksSpringAngularApiApplication  {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworksSpringAngularApiApplication.class, args);
	}

}
