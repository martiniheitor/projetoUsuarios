package com.heitor.workshopmysql.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(unique = true, nullable = false)
	private String cpf;
	private Date dataNascimento;
	private int idade;
	private String sexo;
	private String endereco;

	public User() {
	}

	public User(Long id, String name, String cpf, Date dataNascimento, String sexo, String endereco) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.endereco = endereco;
		this.idade = getIdade(dataNascimento);
	}

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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
		this.idade = getIdade(dataNascimento);
	}

	public int getIdade() {
		return idade;
	}

	public static int getIdade(Date dataNascimento) {
		Date hoje = new Date();
		long idadeEmMillis = hoje.getTime() - dataNascimento.getTime();
		return (int) (idadeEmMillis / (1000L * 60 * 60 * 24 * 365));
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

	@Override
	public int hashCode() {
		return Objects.hash(cpf, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id);
	}

	public static boolean validarCPF(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf == null || cpf.isEmpty() || cpf.equals("00000000000") || cpf.equals("11111111111")
				|| cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444")
				|| cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777")
				|| cpf.equals("88888888888") || cpf.equals("99999999999") || cpf.length() != 11) {
			return false;
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

			return dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10);
		} catch (Exception e) {
			return false;
		}
	}
}