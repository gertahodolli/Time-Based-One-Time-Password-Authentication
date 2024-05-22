package common;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
public class TOTPUtils {
    public static String generateTOTP(String secret) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secret);
        String hexKey = Hex.encodeHexString(bytes);
//        System.out.println("HEXCODEI PER MOMENTIN ESHTE " + hexKey);
//        System.out.println("HEXCODE SIPAS TOTP.getOTP(hexKey): " + TOTP.getOTP(hexKey));
        return TOTP.getOTP(hexKey);
    }
}
