package com.heitor.workshopmysql.resources;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heitor.workshopmysql.dto.UserDTO;
import com.heitor.workshopmysql.entities.User;
import com.heitor.workshopmysql.resources.exceptions.InvalidCpfException;
import com.heitor.workshopmysql.services.UserService;
import com.heitor.workshopmysql.services.exceptions.UserNotFoundException;
import com.heitor.workshopmysql.util.CPFValidator;

@RestController
@RequestMapping("/users")
public class UserResource {

	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserDTO> findAll() {
		List<User> users = userService.findAll();
		return users.stream().map(user -> addIdadeToResponse(user, userService.getIdade(user))).toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		Optional<User> userOpt = userService.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			int idade = userService.getIdade(user);
			return ResponseEntity.ok(addIdadeToResponse(user, idade));
		}
		return ResponseEntity.notFound().build();
	}

	private UserDTO addIdadeToResponse(User user, int idade) {
		return new UserDTO(user.getId(), user.getName(), user.getCpf(), user.getDataNascimento(), idade, user.getSexo(),
				user.getEndereco());
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<UserDTO> findByCPF(@PathVariable String cpf) {
		Optional<User> userOpt = userService.findByCPF(cpf);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			int idade = userService.getIdade(user);
			return ResponseEntity.ok(addIdadeToResponse(user, idade));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<User> create(@RequestBody @Validated User user) {
		logger.debug("Recebendo requisição para criar usuário: {}", user);

		try {
			User createdUser = userService.create(user);
			logger.info("Usuário criado com sucesso: {}", createdUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} catch (InvalidCpfException e) {
			logger.error("Erro de validação de CPF: {}", e.getMessage());
			return ResponseEntity.badRequest().body(null);
		} catch (Exception e) {
			logger.error("Erro inesperado ao criar usuário: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Validated User user) {
		if (!userService.existsById(id)) {
			throw new UserNotFoundException("Usuário com ID " + id + " não encontrado.");
		}
		if (!CPFValidator.isValidCPF(user.getCpf())) {
			throw new InvalidCpfException("CPF inválido: " + user.getCpf());
		}
		user.setId(id);
		User updatedUser = userService.create(user);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!userService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}