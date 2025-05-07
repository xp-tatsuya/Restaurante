package Util;

public class cnpjValidator {

    // Pesos para cálculo do primeiro e do segundo dígito verificador
    private static final int[] WEIGHTS1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] WEIGHTS2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * Valida um CNPJ, removendo pontuação e conferindo dígitos verificadores.
     *
     * @param cnpj String com ou sem formatação (ex: "08.736.621/0001-40" ou "08736621000140")
     * @return true se o CNPJ for válido, false caso contrário
     */
    public static boolean isValid(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        // 1) Remove tudo que não for dígito; ex: "08.736.621/0001-40" → "08736621000140"
        String digits = cnpj.replaceAll("\\D", "");  // :contentReference[oaicite:11]{index=11}

        // 2) Deve ter exatamente 14 dígitos
        if (digits.length() != 14) {
            return false;
        }

        // 3) Rejeita sequências repetidas (por exemplo, "00000000000000")
        if (digits.matches("(\\d)\\1{13}")) {
            return false;
        }

        // 4) Converte caracteres em valores inteiros
        int[] nums = new int[14];
        for (int i = 0; i < 14; i++) {
            nums[i] = Character.getNumericValue(digits.charAt(i));
        }

        // 5) Calcula o primeiro dígito verificador
        int sum1 = 0;
        for (int i = 0; i < WEIGHTS1.length; i++) {
            sum1 += nums[i] * WEIGHTS1[i];
        }
        int digit1 = 11 - (sum1 % 11);
        if (digit1 >= 10) {
            digit1 = 0;
        }

        // 6) Calcula o segundo dígito verificador
        int sum2 = 0;
        for (int i = 0; i < WEIGHTS2.length; i++) {
            sum2 += nums[i] * WEIGHTS2[i];
        }
        int digit2 = 11 - (sum2 % 11);
        if (digit2 >= 10) {
            digit2 = 0;
        }

        // 7) Verifica se os dígitos calculados conferem com os dígitos informados
        return digit1 == nums[12] && digit2 == nums[13];  // :contentReference[oaicite:12]{index=12}
    }
}