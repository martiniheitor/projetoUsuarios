//Classe resposnável pela transferência de dados dos usuários, omitindo informações sensiveis ou desnecessárias.
package com.heitor.workshopmysql.dto;

import javax.persistence.Column;

public class UserDTO {
	private Long id;
	private String name;
	@Column(unique = true, nullable = false)
	private String cpf;
	private int idade;
	private String sexo;
	private String endereco;

	// Construtores
	public UserDTO(Long id, String name, String cpf, int idade, String sexo, String endereco) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.idade = idade;
		this.sexo = sexo;
		this.endereco = endereco;
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}
