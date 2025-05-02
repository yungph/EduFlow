package com.advsoftware.EduFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EduFlowApplication {

	public static void main(String[] args) {

		SpringApplication.run(EduFlowApplication.class, args);

//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String rawPassword = "admin";  // Replace with your raw password
//		String encodedPassword = encoder.encode(rawPassword);
//		System.out.println(encodedPassword);
	}

}
