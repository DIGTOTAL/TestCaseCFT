package cft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
    private final LineHandler lineHandler;

    public FileProcessor(LineHandler lineHandler) {
        this.lineHandler = lineHandler;
    }

    public void process(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineHandler.handle(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + file + ": " + e.getMessage());
        }
    }
}