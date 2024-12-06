package org.example;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;

public class PasswordManager {
    private static final String FILE_PATH = "passwd.txt";
    public Argon2 argon2;
    private final Set<String> worstPasswords;

    public PasswordManager() {
        argon2 = Argon2Factory.create();
        this.worstPasswords = new HashSet<>();
        loadWorstPasswords("10k-worst-passwords.txt");
    }
    public String hashPassword(String password, String salt){
        return argon2.hash(2, 65536, 1, password + salt);
    }
    public boolean verifyPassword(String password, String salt, String hash){
        return argon2.verify(hash, password + salt);
    }
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 128-bit salt
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    public void addUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String salt = generateSalt();
            String hash = hashPassword(password, salt);
            writer.write(username + ":" + salt + ":" + hash);
            writer.newLine();
            System.out.println("User " + username + " added successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the password file: " + e.getMessage());
        }
    }
    public Map<String, String> getUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) {
                    Map<String, String> userRecord = new HashMap<>();
                    userRecord.put("username", parts[0]);
                    userRecord.put("salt", parts[1]);
                    userRecord.put("hash", parts[2]);
                    return userRecord;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from the password file: " + e.getMessage());
        }
        return null;
    }
    private void loadWorstPasswords(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                worstPasswords.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error loading worst passwords list: " + e.getMessage());
        }
    }
    public boolean proActivePasswordChecker(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            System.out.println("Password must contain at least one letter.");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            System.out.println("Password must contain at least one number.");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            System.out.println("Password must contain at least one lowercase letter.");
            return false;
        }

        if (!password.matches(".*[@#$%^&+=].*")) {
            System.out.println("Password must contain at least one special character.");
            return false;
        }

        if (password.matches(".*\\s.*")) {
            System.out.println("Password must not contain any spaces.");
            return false;
        }

        if (password.matches(".*[.,!?].*")) {
            System.out.println("Password must not contain any punctuation.");
            return false;
        }
        if (worstPasswords.contains(password)) {
            System.out.println("This password is too common. Please choose a stronger password.");
            return false;
        }

        System.out.println("Password is valid.");
        return true;
    }
}


