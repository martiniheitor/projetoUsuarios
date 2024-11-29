//Classe que permite acesso e modificações ao banco de dados dos usuários
package com.heitor.workshopmysql.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heitor.workshopmysql.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByCpf(String cpf);
}