package com.heitor.workshopmysql.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.heitor.workshopmysql.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserDTO> findAll() {
		List<User> users = userService.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		Optional<User> userOpt = userService.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return ResponseEntity.ok(convertToDTO(user));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<UserDTO> findByCPF(@PathVariable String cpf) {
		Optional<User> userOpt = userService.findByCPF(cpf);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return ResponseEntity.ok(convertToDTO(user));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<User> create(@RequestBody @Validated User user) {
		if (user.getIdade() <= 0) {
			return ResponseEntity.badRequest().body(null);
		}
		try {
			User createdUser = userService.create(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Validated User user) {
		if (user.getIdade() <= 0) {
			return ResponseEntity.badRequest().build();
		}
		User updatedUser = userService.update(id, user);
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

	private UserDTO convertToDTO(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getCpf(), user.getIdade(), user.getSexo(),
				user.getEndereco());
	}
}
