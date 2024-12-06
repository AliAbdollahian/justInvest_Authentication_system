package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class EnrolUsers {
    private static final String ROLES_FILE = "/Users/ali/Documents/SYSC4810/justInvest _authentication_system/src/main/resources/roles.csv";
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
        String writablePath = getWritableFilePath(ROLES_FILE);
        try (FileWriter writer = new FileWriter(writablePath, true)) {
            writer.append(username).append(",").append(role).append("\n");
        } catch (IOException e) {
            System.err.println("Error saving role to file: " + e.getMessage());
        }
    }

    private String getWritableFilePath(String fileName) {
        String userHome = System.getProperty("user.home"); // User's home directory
        String appDir = userHome + File.separator + "justInvest"; // App-specific directory
        File dir = new File(appDir);
        if (!dir.exists()) dir.mkdirs(); // Create directory if not exists
        return appDir + File.separator + fileName;
    }


}

