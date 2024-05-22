package server;

import common.Config;
import common.TOTPUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_PORT);
             PrintWriter logWriter = new PrintWriter(new FileWriter("server_log.txt", true))) {
            System.out.println("Server started and awaiting authentication requests...");
            String totpPerKlientin = TOTPUtils.generateTOTP(Config.SECRET);
            System.out.println("TOTP sent to the client: " + totpPerKlientin);
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String username = in.readLine();
                    String totp = in.readLine();

                    System.out.println("Received authentication request for user: " + username);
                    System.out.println("TOTP provided by user: " + totp);

                    boolean isValid = totpPerKlientin.equals(totp);
                    if (isValid) {
                        out.println("Authentication successful for user: " + username);
                        System.out.println("Authentication successful for user: " + username);
                        logWriter.println("User: " + username + " - Authentication successful.");
                    } else {
                        out.println("Authentication failed for user: " + username);
                        System.out.println("Authentication failed for user: " + username);
                        logWriter.println("User: " + username + " - Authentication failed.");
                    }
                    logWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
