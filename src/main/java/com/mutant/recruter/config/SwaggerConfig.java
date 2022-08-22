package com.mutant.recruter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig{
	
		@Bean
	    public Docket api(){
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.mutant.recruter.controller"))
	                .paths(PathSelectors.regex("/recruter.*"))
	                .build()
	                .apiInfo(apiInfo())
	                .tags(new Tag("MutantController", "Mutant Controller"));
	    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Meli Chalenge API")
				.description("API to recruit mutants")
				.contact(new Contact("Marcos Tuseddo", "", "tuseddomarcos@gmail.com"))
				.termsOfServiceUrl("").license("")
				.licenseUrl("tuseddomarcos@gmail.com").version("1.0").build();
	}
}