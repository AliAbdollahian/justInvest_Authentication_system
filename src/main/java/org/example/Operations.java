package org.example;

public class Operations {
    public static void perform(int option) {
        switch (option) {
            case 1 -> System.out.println("Viewing account balance...");
            case 2 -> System.out.println("Viewing investment portfolio...");
            case 3 -> System.out.println("Modifying investment portfolio...");
            case 4 -> System.out.println("Viewing Financial Advisor contact info...");
            case 5 -> System.out.println("Viewing Financial Planner contact info...");
            case 6 -> System.out.println("Viewing money market instruments...");
            case 7 -> System.out.println("Viewing private consumer instruments...");
            case 8 -> System.out.println("Accessing system during business hours...");
            default -> System.out.println("Invalid operation selected.");
        }
    }
}
