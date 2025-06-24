package ftn.bp2.ui;

import ftn.bp2.util.InputHandler;
import java.util.Scanner;

public class VinarijaUIHandler {

    private final Scanner scanner;
    private final InputHandler inputHandler;
    private final GrapeVarietyWineCountUIHandler grapeVarietyWineCountUIHandler;
    private final WineSalesWithGrapeVarietiesUIHandler wineSalesWithGrapeVarietiesUIHandler;
    private final CustomerAnalysisReportUIHandler customerAnalysisReportUIHandler;
    private final CustomerOrderTransactionUIHandler customerOrderTransactionUIHandler;

    public VinarijaUIHandler() {
        this.scanner = new Scanner(System.in);
        this.inputHandler = new InputHandler();
        this.grapeVarietyWineCountUIHandler = new GrapeVarietyWineCountUIHandler();
        this.wineSalesWithGrapeVarietiesUIHandler = new WineSalesWithGrapeVarietiesUIHandler();
        this.customerAnalysisReportUIHandler = new CustomerAnalysisReportUIHandler();
        this.customerOrderTransactionUIHandler = new CustomerOrderTransactionUIHandler();
    }

    public void start() {
        System.out.println("=== DOBRODOŠLI U SISTEM VINARIJE ===");
        while (true) {
            displayMainMenu();
            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        handleReportOperations();
                        break;
                    case 2:
                        handleTransactionOperations();
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


    private void handleReportOperations(){
        while (true) {
            System.out.println("\n=== IZVEŠTAJI ===");
            System.out.println("1. Broj vina po sorti grožđa");
            System.out.println("2. Analiza prodaja vina sa sortama grožđa");
            System.out.println("3. Analiza kupaca i preferencija");
            System.out.println("0. Nazad");

            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            switch (choice) {
                case 1:
                    try {
                        grapeVarietyWineCountUIHandler.start();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom generisanja izveštaja: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        wineSalesWithGrapeVarietiesUIHandler.start();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom generisanja izveštaja: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        customerAnalysisReportUIHandler.start();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom generisanja izveštaja: " + e.getMessage());
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Nevažeća opcija.");
            }
        }
    }

    private void handleTransactionOperations() throws Exception {
        while (true) {
            System.out.println("\n=== TRANSAKCIJE ===");
            System.out.println("1. Kompletna narudžba kupca");
            System.out.println("0. Nazad");

            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            switch (choice) {
                case 1:
                    try {
                        customerOrderTransactionUIHandler.start();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom izvršavanja transakcije: " + e.getMessage());
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Nevažeća opcija.");
            }
        }
    }
    private void displayMainMenu() {
        System.out.println("\n=== GLAVNI MENI ===");
        System.out.println("1. Izvještaji");
        System.out.println("2. Transakcije");
        System.out.println("0. Izlaz");
    }
}
