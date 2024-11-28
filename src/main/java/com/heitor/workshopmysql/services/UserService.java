package com.heitor.workshopmysql.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heitor.workshopmysql.entities.User;
import com.heitor.workshopmysql.repositories.UserRepository;
import com.heitor.workshopmysql.resources.exceptions.InvalidCpfException;
import com.heitor.workshopmysql.services.exceptions.UserNotFoundException;
import com.heitor.workshopmysql.util.CPFValidator;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public User create(User user) {
		validateUser(user);
		return userRepository.save(user);
	}

	public List<User> findAll() {
		logger.debug("Buscando todos os usuários");
		return userRepository.findAll();
	}

	public Optional<User> findById(Long id) {
		logger.debug("Buscando usuário pelo ID: {}", id);
		return userRepository.findById(id);
	}

	public Optional<User> findByCPF(String cpf) {
		logger.debug("Buscando usuário pelo CPF: {}", cpf);
		return userRepository.findByCpf(cpf);
	}

	public User update(Long id, User user) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("Usuário não encontrado com ID: " + id);
		}
		validateUser(user);
		user.setId(id);
		return userRepository.save(user);
	}

	public void delete(Long id) {
		logger.debug("Deletando usuário com ID: {}", id);
		userRepository.deleteById(id);
		logger.info("Usuário deletado com sucesso, ID: {}", id);
	}

	public boolean existsById(Long id) {
		logger.debug("Verificando se usuário existe com ID: {}", id);
		return userRepository.existsById(id);
	}

	private void validateUser(User user) {
		if (!CPFValidator.isValidCPF(user.getCpf())) {
			throw new InvalidCpfException("CPF inválido!");
		}
	}
}
