package org.example;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UIManager {
    private PasswordManager pm = new PasswordManager();
    private EnrolUsers enrolUsers = new EnrolUsers();

    public void displayUI() {
        System.out.println("Welcome to JustInvest System");
        System.out.println("-------------------------------------");
        System.out.println("Operations available on the system:");
        System.out.println("1. View account balance\n" +
                "2. View investment portfolio\n" +
                "3. Modify investment portfolio\n" +
                "4. View Financial Advisor contact info\n" +
                "5. View Financial Planner contact info\n" +
                "6. View money market instruments\n" +
                "7. View private consumer instruments");
        System.out.println("-------------------------------------");

        Scanner scanner = new Scanner(System.in);
        String username = getUsername(scanner);
        Map<String, String> user = pm.getUser(username);

        if (user == null) {
            handleAccountCreation(scanner, username);
            user = pm.getUser(username);
        }

        boolean accessGranted = login(scanner, user);
        if (!accessGranted) {
            System.out.println("Exiting system. Goodbye!");
            return;
        }

        System.out.println("\nACCESS GRANTED!");
        Role role = AccessControlManager.getUserRole(username);

        if (role == null) {
            System.out.println("ACCESS GRANTED but User role not found.");
            return;
        }

        System.out.println("Your role: " + role);
        Set<Integer> authorizedOperations = RolePermissions.getAuthorizedOperations(role);

        if (authorizedOperations.isEmpty()) {
            System.out.println("No authorized operations for your role.");
            return;
        }

        System.out.println("Authorized operations: " + authorizedOperations);

        while (true) {
            System.out.print("Enter the operation number to perform (or 0 to exit): ");
            int option = scanner.nextInt();

            if (option == 0) {
                System.out.println("Exiting system. Goodbye!");
                return;
            }

            if (authorizedOperations.contains(option)) {
                Operations.perform(option);
            } else {
                System.out.println("Operation not allowed for your role. Try again.");
            }
        }
    }

    private String getUsername(Scanner scanner) {
        System.out.print("Enter your username: ");
        return scanner.nextLine();
    }

    private void handleAccountCreation(Scanner scanner, String username) {
        System.out.println("No account found for username: " + username);
        System.out.print("Would you like to create an account? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            if (pm.getUser(username) != null) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }
            enrolUsers.enrolUser(username);
            System.out.println("Account created successfully. Please log in again.\n");
        } else {
            System.out.println("Exiting system. Goodbye!");
        }

    }

    public boolean login(Scanner scanner, Map<String, String> user) {
        if (user == null) {
            System.out.println("User not found, cannot proceed with login.");
            return false;
        }
        boolean accessGranted = false;
        while (!accessGranted) {
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (pm.verifyPassword(password, user.get("salt"), user.get("hash"))) {
                accessGranted = true;
            } else {
                System.out.println("ACCESS DENIED: Incorrect password. Try again.");
            }
        }
        return accessGranted;
    }
}

