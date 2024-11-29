//Classe intermediária entre Resource e Repository, contém a lógica das operações de manipulação de dados dos usuários
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

	//Ferramenta de registro de informações, como registro de ações, leitura de erros e debbuging.
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	//Permite a conexão com a classe Repository
	@Autowired
	private UserRepository userRepository;

	//Criação de usuários no banco de dados
	public User create(User user) {
		validateUser(user);
		return userRepository.save(user);
	}

	//Requisição dos dados de todos os usuários
	public List<User> findAll() {
		logger.debug("Buscando todos os usuários");
		return userRepository.findAll();
	}

	//Leitura de usuários a partir do Id
	public Optional<User> findById(Long id) {
		logger.debug("Buscando usuário pelo ID: {}", id);
		return userRepository.findById(id);
	}

	//Leitura de usuários a partir do CPF
	public Optional<User> findByCPF(String cpf) {
		logger.debug("Buscando usuário pelo CPF: {}", cpf);
		return userRepository.findByCpf(cpf);
	}

	//Atualização dos dados dos usuários a partir do id
	public User update(Long id, User user) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("Usuário não encontrado com ID: " + id);
		}
		validateUser(user);
		user.setId(id);
		return userRepository.save(user);
	}

	//Delete de usuários a partir do Id
	public void delete(Long id) {
		logger.debug("Deletando usuário com ID: {}", id);
		userRepository.deleteById(id);
		logger.info("Usuário deletado com sucesso, ID: {}", id);
	}

	//Função responsável por validar a existência do usuário a partir do id dele
	public boolean existsById(Long id) {
		logger.debug("Verificando se usuário existe com ID: {}", id);
		return userRepository.existsById(id);
	}

	//Função responsável por validar a existência do CPF a partir do cálculo da classe CPFValidator
	private void validateUser(User user) {
		if (!CPFValidator.isValidCPF(user.getCpf())) {
			throw new InvalidCpfException("CPF inválido!");
		}
	}
}
