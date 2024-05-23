package common;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

public class TOTPUtils {
    public static String generateTOTP(String secret) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secret);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
    public static String encrypt(String plaintext, BigInteger bigKey) {
        int key = bigKey.intValue();
        StringBuilder encrypted = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            if (Character.isDigit(ch)) {
                int digit = Character.getNumericValue(ch);
                int encryptedDigit = (digit + key) % 10;
                if (encryptedDigit < 0) {
                    encryptedDigit += 10;
                }
                encrypted.append(encryptedDigit);
            } else {
                encrypted.append(ch);
            }
        }
        return encrypted.toString();
    }
    public static String decrypt(String ciphertext, BigInteger bigKey) {
        int key = bigKey.intValue();
        return encrypt(ciphertext, BigInteger.valueOf(-key));
    }
    public static double generateRandomNumber(double low, double high){
        return (Math.random()*(low-high))+low;
    }
}
