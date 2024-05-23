package client;

import common.Config;
import common.TOTPUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.StringTemplate.STR;

public class Client {
    private static final BigInteger G = BigInteger.valueOf(13);
    private static final BigInteger P = BigInteger.valueOf(5);

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(Config.SERVER_HOST, Config.SERVER_PORT);
            ObjectOutputStream clientOutput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());
            PrintWriter logWriter = new PrintWriter(new FileWriter("client_log.txt", true));

            // ~~~~~~~~~~~~~~~~~
            System.out.println("Communicating DHM");
            BigInteger B = BigInteger.valueOf((long) TOTPUtils.generateRandomNumber(0, 10000));
            BigInteger calculatedClientValue = G.modPow(B, P);
            BigInteger valueFromServer = (BigInteger) clientInput.readObject();
            clientOutput.writeObject(calculatedClientValue);
            BigInteger exchagedKey = valueFromServer.modPow(B, P);
            System.out.println(STR."Client and server agreed on [\{exchagedKey}] as the final key");
            // ~~~~~~~~~~~~~~~~~

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your TOTP: ");
            String totp = scanner.nextLine();

            clientOutput.writeObject(username);
            clientOutput.writeObject(totp);

            logWriter.println(STR."Sent to server - Username: \{username}, TOTP: \{totp}");

            System.out.println((clientInput.readObject()).toString());

            logWriter.flush();
            logWriter.close();

            socket.close();

            clientOutput.close();
            clientInput.close();
        } catch (Exception e) {
            System.err.println(STR."Client side error...\n\{e.getMessage()}");
            System.err.println(STR."Error type: \{e.getClass()}");
        }
    }
}