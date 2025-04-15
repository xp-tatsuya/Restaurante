package Util;

public class telefoneValidator {
	
	public static boolean validarTelefone(String telefone) {
		
		if (telefone == null || !telefone.matches("\\d{11}")) {
		return false;
		}

		String ddd = telefone.substring(0, 2);
		if (Integer.parseInt(ddd) < 11 || Integer.parseInt(ddd) > 99) {
		return false;
		}

		char primeiroDigito = telefone.charAt(2);
		if (primeiroDigito != '9') {
		return false;
		}

		return true;
		}

}
