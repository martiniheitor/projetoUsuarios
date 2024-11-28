package com.heitor.workshopmysql.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.heitor.workshopmysql.entities.User;
import com.heitor.workshopmysql.repositories.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		userRepository.deleteAll();

		User user1 = new User(null, "Heitor Martini de Oliveira", "41478719842", 20, "M", "Rua Cayowaá 1210, Apt. 15");

		userRepository.saveAll(Arrays.asList(user1));
	}
}
