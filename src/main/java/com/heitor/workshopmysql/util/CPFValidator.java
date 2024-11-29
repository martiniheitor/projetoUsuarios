//Classe que armazena a lógica por trás da validação do CPF
package com.heitor.workshopmysql.util;

public class CPFValidator {

	public static boolean isValidCPF(String cpf) {
		if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
			return false;
		}
		try {
			int sum1 = 0, sum2 = 0;
			for (int i = 0; i < 9; i++) {
				int digit = Character.getNumericValue(cpf.charAt(i));
				sum1 += digit * (10 - i);
				sum2 += digit * (11 - i);
			}
			int check1 = (sum1 * 10) % 11;
			check1 = check1 == 10 ? 0 : check1;
			int check2 = ((sum2 + check1 * 2) * 10) % 11;
			check2 = check2 == 10 ? 0 : check2;
			return check1 == Character.getNumericValue(cpf.charAt(9))
					&& check2 == Character.getNumericValue(cpf.charAt(10));
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
