package server;

import common.Config;
import common.TOTPUtils;
import org.apache.commons.codec.binary.Base32;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final BigInteger G = BigInteger.valueOf(13);
    private static final BigInteger P = BigInteger.valueOf(5);
    public static void main(String[] args) {
        System.out.println("Server started.");
        try{
            ServerSocket serverSocket = new ServerSocket(Config.SERVER_PORT);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server connected  and awaiting authentication requests.");

            ObjectOutputStream serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());
            PrintWriter logWriter = new PrintWriter(new FileWriter("server_log.txt", true));

            // ~~~~~~~~~~~~~~~~~
            System.out.println("Communicating DHM");
            BigInteger A = BigInteger.valueOf((long) common.TOTPUtils.generateRandomNumber(0,10000));
            BigInteger calculatedServerValue = G.modPow(A, P);
            System.out.println(STR."Message sent to the client is: \{calculatedServerValue}");
            serverOutput.writeObject(calculatedServerValue);
            BigInteger valueFromClient = (BigInteger) serverInput.readObject();
            System.out.println(STR."Value accepted from the client is: \{valueFromClient}");
            BigInteger exchagedKey = valueFromClient.modPow(A, P);
            System.out.println(STR."Client and server agreed on [\{exchagedKey}] as the final key");
            // ~~~~~~~~~~~~~~~~~

            byte[] bytes = exchagedKey.toByteArray();
            Base32 base32 = new Base32();

            String encodedExchangedKey = base32.encodeToString(bytes);

            String totpForUser = TOTPUtils.generateTOTP(encodedExchangedKey);
            String totpForUser_encrypted = TOTPUtils.encrypt(totpForUser, exchagedKey);
            System.out.println(STR."Decrypted TOTP sent to the client: \{totpForUser}");
            System.out.println(STR."Encrypted TOTP sent to the client: \{totpForUser_encrypted}");

            String username = (String) serverInput.readObject();
            String totpFromUser_encrypted = (String) serverInput.readObject();

            System.out.println(STR."Received authentication request for user: \{username}");

            String totpFromUser_decrypted = TOTPUtils.decrypt(totpFromUser_encrypted, exchagedKey);
            System.out.println(STR."Decrypted TOTP provided by user: \{totpFromUser_decrypted}");
            System.out.println(STR."Encrypted TOTP provided by user: \{totpFromUser_encrypted}");

            boolean isValid = totpFromUser_decrypted.equals(totpForUser);
            if (isValid) {
                serverOutput.writeObject(STR."Authentication successful for user: \{username}");
                System.out.println(STR."Authentication successful for user: \{username}");
                logWriter.println(STR."User: \{username} - Authentication successful.");
            } else {
                serverOutput.writeObject(STR."Authentication failed for user: \{username}");
                System.out.println(STR."Authentication failed for user: \{username}");
                logWriter.println(STR."User: \{username} - Authentication failed.");
            }

            serverSocket.close();
            serverOutput.close();
            serverInput.close();

            clientSocket.close();

            logWriter.flush();
            logWriter.close();
        } catch (Exception e) {
            System.err.println(STR."Server side error...\n\{e.getMessage()}");
        }
    }
}
