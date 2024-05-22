package client;

import common.Config;
import common.TOTPUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to TOTP Authentication System.");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your TOTP: ");
        String totp = scanner.nextLine();

        try (Socket socket = new Socket(Config.SERVER_HOST, Config.SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter logWriter = new PrintWriter(new FileWriter("client_log.txt", true))) {

            out.println(username);
            out.println(totp);

            String response = in.readLine();
            System.out.println(response);


            logWriter.println("Sent to server - Username: " + username + ", TOTP: " + totp);
            logWriter.println("Received from server - " + response);
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
