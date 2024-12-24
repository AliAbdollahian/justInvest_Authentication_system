package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
    public void enrolUser(String username, String password, String role) {
        if (!ROLES.containsKey(role)) {
            System.out.println("Invalid role: " + role + ". Skipping user: " + username);
            return;
        }

        pm.addUser(username, password);
        saveUserRole(username, role);
        System.out.println("User " + username + " enrolled successfully with role: " + role);
    }

    public void bulkEnrollUsers() {
        String defaultPassword = "defaultPassword123@";
        List<String[]> users = List.of(
                new String[]{"Sasha Kim", "CLIENT"},
                new String[]{"Emery Blake", "CLIENT"},
                new String[]{"Noor Abbasi", "PREMIUM_CLIENT"},
                new String[]{"Zuri Adebayo", "PREMIUM_CLIENT"},
                new String[]{"Mikael Chen", "FINANCIAL_ADVISOR"},
                new String[]{"Jordan Riley", "FINANCIAL_ADVISOR"},
                new String[]{"Ellis Nakamura", "FINANCIAL_PLANNER"},
                new String[]{"Harper Diaz", "FINANCIAL_PLANNER"},
                new String[]{"Alex Hayes", "TELLER"},
                new String[]{"Adair Patel", "TELLER"}
        );

        for (String[] user : users) {
            String username = user[0];
            String role = user[1];
            // Directly pass the role without validation
            pm.addUser(username, defaultPassword); // Add user to PasswordManager
            saveUserRole(username, role); // Save user-role mapping
            System.out.println("User " + username + " enrolled successfully with role: " + role);
        }
    }
}

