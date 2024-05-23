package common;

import java.math.BigInteger;

public class Config {
    private static final BigInteger G = BigInteger.valueOf(13);
    private static final BigInteger P = BigInteger.valueOf(11);
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 4000;
    public static BigInteger getG() {
        return G;
    }
    public static BigInteger getP() {
        return P;
    }
}
