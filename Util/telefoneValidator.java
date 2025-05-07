package Util;

public class telefoneValidator {

    /**
     * Valida um telefone móvel brasileiro de 11 dígitos,
     * removendo formatação (espaços, parênteses, hífens etc.).
     *
     * @param telefone String com ou sem formatação (ex: "(11) 98765-4321" ou "11987654321")
     * @return true se for um número móvel válido com DDD oficial do Brasil
     */
    public static boolean isValid(String telefone) {
        if (telefone == null) {
            return false;
        }

        // Remove tudo que não for dígito; ex: "(11) 98765-4321" → "11987654321"
        String digits = telefone.replaceAll("\\D", "");

        // Deve ter exatamente 11 dígitos
        if (!digits.matches("\\d{11}")) {
            return false;
        }

        // Valida DDD: dois dígitos de 1–9 (aceita 11–99, exclui X0)
        String ddd = digits.substring(0, 2);
        if (!ddd.matches("[1-9][1-9]")) {
            return false;
        }

        // O primeiro dígito do número (3º caractere) deve ser '9' (móvel)
        if (digits.charAt(2) != '9') {
            return false;
        }

        return true;
    }
}