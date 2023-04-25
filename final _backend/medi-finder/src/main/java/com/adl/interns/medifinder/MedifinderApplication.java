package com.adl.interns.medifinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableWebSecurity
@ComponentScan(basePackages = "com.adl.interns.medifinder")
@EnableJpaRepositories("com.adl.interns.medifinder.repository")
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MedifinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedifinderApplication.class, args);
	}

}
