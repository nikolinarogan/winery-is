package ftn.bp2.ui;

import java.util.Scanner;

public class VinarijaUIHandler {

    private final Scanner scanner;

    public VinarijaUIHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== DOBRODOŠLI U SISTEM VINARIJE ===");
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 0:
                        System.out.println("Hvala što ste koristili sistem vinarije!");
                        return;
                    default:
                        System.out.println("Nevažeća opcija. Pokušajte ponovo.");
                }
            } catch (Exception e) {
                System.err.println("Greška: " + e.getMessage());
            }
            System.out.println("\nPritisnite Enter za nastavak...");
            scanner.nextLine();
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== GLAVNI MENI ===");
        System.out.println("1. Operacije sa vinogradima");
        System.out.println("2. Izvještaji");
        System.out.println("3. Transakcije");
        System.out.println("0. Izlaz");
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Unesite validan broj: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private Integer getOptionalIntInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private float getFloatInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextFloat()) {
            System.out.print("Unesite validan decimalni broj: ");
            scanner.next();
        }
        float value = scanner.nextFloat();
        scanner.nextLine(); // consume newline
        return value;
    }

    private Float getOptionalFloatInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
