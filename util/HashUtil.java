package util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class HashUtil {

    public static String hash(String input) {
        return BCrypt.withDefaults().hashToString(10, input.toCharArray());
    }

    public static boolean verifyHash(String originalPin, String hashedPin) {
        return BCrypt.verifyer().verify(originalPin.toCharArray(), hashedPin).verified;
    }

    public static void main(String[] args) {
        String hash1234 = hash("1234");
        String hash4321 = hash("4321");

        System.out.println("Hash for 1234: " + hash1234);
        System.out.println("Hash for 4321: " + hash4321);
    }
}
