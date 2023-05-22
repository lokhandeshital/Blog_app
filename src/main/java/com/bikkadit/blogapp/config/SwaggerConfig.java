package com.bikkadit.blogapp.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bikkadit.blogapp.helper.SecurityConstant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	private ApiKey apiKeys() {

		return new ApiKey("JWT", SecurityConstant.AUTHORIZATION_HEADER, "header");

	}
	
	private List<SecurityContext> securityContext(){
		return Arrays.asList(SecurityContext.builder().securityReferences(securityRef()).build());
	}
	
	private List<SecurityReference> securityRef(){
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext()) //for security config
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getInfo() {

		return new ApiInfo("Blogging Application", "This Project Developed by Shital", "1.0", "Terms of Service",
				new Contact("Shital", "www.BikkadIt.com", "Shital@1436gmail.com"), "License of Apis", "Api License URL",
				Collections.emptyList());

	}
}
