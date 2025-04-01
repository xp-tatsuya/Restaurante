package Util;

public class cpfValidator {

    public static boolean validarCPF(String cpf) {
        if (cpf == null) {
            return false;
        }
        
        // Remove todos os caracteres que não sejam dígitos
        cpf = cpf.replaceAll("\\D", "");
        
        // Verifica se o CPF possui exatamente 11 dígitos
        if (!cpf.matches("\\d{11}")) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        int soma = 0;
        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) {
            digito1 = 0;
        }
        
        soma = 0;
        // Calcula o segundo dígito verificador
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) {
            digito2 = 0;
        }
        
        // Compara os dígitos calculados com os dígitos informados
        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
               digito2 == Character.getNumericValue(cpf.charAt(10));
    }
}
