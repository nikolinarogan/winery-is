package ftn.bp2.util;

import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Unesite validan broj: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public float getFloatInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextFloat()) {
            System.out.print("Unesite validan decimalni broj: ");
            scanner.next();
        }
        float value = scanner.nextFloat();
        scanner.nextLine();
        return value;
    }


    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Unesite validan decimalni broj: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

}
