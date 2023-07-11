package com.digitalbooking.digitalbooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
		servers = {
				@Server(url = "/", description = "Default Server URL")
		}
)
@SpringBootApplication
public class DigitalBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBookingApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
				return  new OpenAPI()
				.info( new Info()
						.title("Digital Booking API")
						.version("1.1.0")
						.description("Documentação do Digital Booking")
						.termsOfService("http://www.seagger.io/terms/")
						.license(new License().name("Apache2.0").url("http://springdoc.org")));

	}

}
