package org.geekbang.week04;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author LiYue
 * Date: 2019/9/20
 */
public class StorageService {

    private final Path filePath;

    public StorageService(File file) {
        this.filePath = file.toPath();
    }

    public synchronized void save(String msg) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write(msg, 0, msg.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String load() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}