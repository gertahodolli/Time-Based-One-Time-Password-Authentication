package client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    private static final String LOG_FILE_PATH = "client/clientlog.txt";
    public static void log(String message) {
        try (FileWriter writer = new FileWriter(new File(LOG_FILE_PATH), true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}
