//Instanciação de usuário(s) para teste
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

		User user2 = new User(null, "Ana Caroplina", "12345678909", 19, "F", "Rua Rui Barbosa, 333");
		User user3 = new User(null, "Gustavo", "41478719842", 18, "M", "Av. Mutinga, 951");

		userRepository.saveAll(Arrays.asList(user2, user3));
	}
}
