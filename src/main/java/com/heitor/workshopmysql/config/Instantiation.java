package com.heitor.workshopmysql.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		userRepository.deleteAll();

		User user1 = new User(null, "Heitor Martini de Oliveira", "41478719842",
				LocalDate.parse("03/10/2004", formatter), "M", "Rua Cayowaá 1210, Apt. 15");
		user1.setIdade(20);

		userRepository.saveAll(Arrays.asList(user1));
	}
}
