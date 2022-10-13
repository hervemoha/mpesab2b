package com.infosetgroup.amv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootApplication
public class Amv2Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Amv2Application.class, args);
	}

}
