package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class EnrolUsers {
    private static final String ROLES_FILE = "roles.csv";
    PasswordManager pm = new PasswordManager();

    private static final Map<Integer, String> ROLES = Map.of(
            1, "CLIENT",
            2, "PREMIUM_CLIENT",
            3, "FINANCIAL_ADVISOR",
            4, "FINANCIAL_PLANNER",
            5, "TELLER",
            6, "EMPLOYEE"
    );
    public void enrolUser(String username) {
        Scanner scanner = new Scanner(System.in);
        String password;
        boolean isPasswordValid;
        do {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            isPasswordValid = pm.proActivePasswordChecker(password);
        } while (!isPasswordValid);

        System.out.println("Select your role:");
        ROLES.forEach((key, value) -> System.out.println(key + ". " + value));
        System.out.print("Enter the number corresponding to your role: ");
        int roleNumber = scanner.nextInt();
        scanner.nextLine();
        String role = ROLES.get(roleNumber);
        if (role == null) {
            System.out.println("Invalid role selection. Please try again.");
            return;
        }
        pm.addUser(username, password);
        saveUserRole(username, role);

        System.out.println("User " + username + " enrolled successfully with role: " + role);
    }

    private void saveUserRole(String username, String role) {
        try (FileWriter writer = new FileWriter(ROLES_FILE, true)) {
            writer.append(username).append(",").append(role).append("\n");
        } catch (IOException e) {
            System.err.println("Error saving role to file: " + e.getMessage());
        }
    }
}

