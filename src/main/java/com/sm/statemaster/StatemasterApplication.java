package com.sm.statemaster;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "State master"
		)
)
public class StatemasterApplication {

	public static void main(String[] args) {
		SpringApplication.    run(StatemasterApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
