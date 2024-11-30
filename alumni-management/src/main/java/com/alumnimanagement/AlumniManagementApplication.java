package com.alumnimanagement;

import com.alumnimanagement.config.KeycloakConfiguration;
import com.alumnimanagement.config.KeycloakConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakConfigurationProperties.class)
@Import(KeycloakConfiguration.class)
public class AlumniManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumniManagementApplication.class, args);
	}

}
