package com.heitor.workshopmysql.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public User create(User user) {
		logger.debug("Criando usuário: {}", user);

		if (!CPFValidator.isValidCPF(user.getCpf())) {
			logger.error("CPF inválido: {}", user.getCpf());
			throw new InvalidCpfException("CPF inválido!");
		}

		user.setDataNascimento(LocalDate.parse(user.getDataNascimento().toString(), formatter));
		int idade = getIdade(user);
		user.setIdade(idade); // Atualiza a idade
		User savedUser = userRepository.save(user);

		logger.info("Usuário criado com sucesso: {}", savedUser);
		return savedUser;
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
		logger.debug("Atualizando usuário com ID: {}", id);

		if (!userRepository.existsById(id)) {
			logger.error("Usuário não encontrado com ID: {}", id);
			throw new UserNotFoundException("Usuário não encontrado!");
		}

		if (!CPFValidator.isValidCPF(user.getCpf())) {
			logger.error("CPF inválido para atualização: {}", user.getCpf());
			throw new InvalidCpfException("CPF inválido!");
		}

		int idade = getIdade(user);
		user.setIdade(idade); // Atualiza a idade
		user.setId(id);
		User updatedUser = userRepository.save(user);

		logger.info("Usuário atualizado com sucesso: {}", updatedUser);
		return updatedUser;
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

	public int getIdade(User user) {
		if (user.getDataNascimento() == null) {
			logger.warn("Data de nascimento não fornecida para usuário: {}", user);
			return 0;
		}
		LocalDate hoje = LocalDate.now();
		LocalDate nascimento = user.getDataNascimento();
		int idade = Period.between(nascimento, hoje).getYears();
		logger.debug("Idade calculada para o usuário {}: {}", user.getName(), idade);
		return idade;
	}
}
