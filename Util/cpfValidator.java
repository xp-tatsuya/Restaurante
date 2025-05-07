package Util;

public class cpfValidator {

    // Pesos para cálculo dos dígitos
    private static final int[] PESOS1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS2 = {11,10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isValid(String cpf) {
        if (cpf == null) {
            return false;
        }
        // Remove espaços nas extremidades e tudo que não for dígito
        String digits = cpf.trim().replaceAll("\\D", "");
        // Deve ter 11 dígitos
        if (!digits.matches("\\d{11}")) {
            return false;
        }
        // Descarta sequências repetidas
        if (digits.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < PESOS1.length; i++) {
            soma += Character.getNumericValue(digits.charAt(i)) * PESOS1[i];
        }
        int dv1 = 11 - (soma % 11);
        if (dv1 >= 10) {
            dv1 = 0;
        }

        // Calcula segundo dígito verificador
        soma = 0;
        for (int i = 0; i < PESOS2.length; i++) {
            soma += Character.getNumericValue(digits.charAt(i)) * PESOS2[i];
        }
        int dv2 = 11 - (soma % 11);
        if (dv2 >= 10) {
            dv2 = 0;
        }

        // Compara com os dígitos informados
        return dv1 == Character.getNumericValue(digits.charAt(9))
            && dv2 == Character.getNumericValue(digits.charAt(10));
    }
}