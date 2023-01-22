package com.example.blog;

import com.example.blog.enumeration.Role;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RevenueDBManager {

	public static void main(String[] args) {
		SpringApplication.run(RevenueDBManager.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository repository){
		return args -> {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			var user = User
					.builder()
					.role(Role.ADMIN)
					.email("ckoomson75@gmal.com")
					.lastname("koomson")
					.username("clem75")
					.password(passwordEncoder.encode("admin"))
					.firstname("clement")
					.isEnabled(Boolean.TRUE)
					.build();
			repository.save(user);
		};
	}

}
