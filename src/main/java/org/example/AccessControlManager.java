package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AccessControlManager {
    public static Role getUserRole(String username) {
        try (Scanner fileScanner = new Scanner(new File("/Users/ali/Documents/SYSC4810/justInvest _authentication_system/src/main/resources/roles.csv"))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split(",");
                if (userData[0].trim().equalsIgnoreCase(username)) {
                    return Role.valueOf(userData[1].trim().toUpperCase().replaceAll("\\s+", "_"));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: The roles.csv file was not found.");
        }
        return null;
    }
}

