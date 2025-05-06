package Util;

public class cnpjValidator {

    private static final int[] WEIGHTS1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] WEIGHTS2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isValid(String cnpj) {
        if (cnpj == null) return false;
        String digits = cnpj.replaceAll("\\D", "");
        if (digits.length() != 14) return false;
        if (digits.matches("(\\d)\\1{13}")) return false;

        int[] nums = new int[14];
        for (int i = 0; i < 14; i++) {
            nums[i] = digits.charAt(i) - '0';
        }

        int sum1 = 0;
        for (int i = 0; i < 12; i++) {
            sum1 += nums[i] * WEIGHTS1[i];
        }
        int r1 = sum1 % 11;
        int v1 = (r1 < 2) ? 0 : 11 - r1;

        int sum2 = 0;
        for (int i = 0; i < 13; i++) {
            sum2 += nums[i] * WEIGHTS2[i];
        }
        int r2 = sum2 % 11;
        int v2 = (r2 < 2) ? 0 : 11 - r2;

        return v1 == nums[12] && v2 == nums[13];
    }
}
