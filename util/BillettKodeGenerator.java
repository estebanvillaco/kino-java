package util;

import java.security.SecureRandom;

public class BillettKodeGenerator {
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String genererKode() {
        StringBuilder sb = new StringBuilder();

        // 4 bokstaver
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }

        // 2 siffer
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(index));
        }

        return sb.toString();
    }
}
