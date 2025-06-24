package ftn.bp2.ui;

import ftn.bp2.dto.GrapeVarietyWineCountDTO;
import ftn.bp2.service.GrapeVarietyWineCountService;
import ftn.bp2.util.InputHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GrapeVarietyWineCountUIHandler {
    private final Scanner scanner;
    private final GrapeVarietyWineCountService grapeVarietyWineCountService;
    private final InputHandler inputHandler;

    public GrapeVarietyWineCountUIHandler() {
        this.scanner = new Scanner(System.in);
        this.grapeVarietyWineCountService = new GrapeVarietyWineCountService();
        this.inputHandler = new InputHandler();
    }

    public void start() {
        System.out.println("=== IZVEŠTAJ: BROJ VINA PO SORTI GROŽĐA ===");
        while (true) {
            displayMenu();
            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        displayAllGrapeVarietyWineCount();
                        break;
                    case 0:
                        System.out.println("Povratak na glavni meni...");
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

    private void displayMenu() {
        System.out.println("\n=== MENI IZVEŠTAJA ===");
        System.out.println("1. Prikaži sve sorte grožđa sa brojem vina u kojima učestvuju");
        System.out.println("0. Nazad");
    }

    private void displayAllGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getGrapeVarietyWineCount();

        System.out.println("\n=== BROJ VINA PO SORTI GROŽĐA ===");
        System.out.printf("%-30s %-15s %-20s%n", "Sorta grožđa", "Broj vina", "Godina najstarijeg vina");
        System.out.println("-".repeat(65));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d %-20d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines(),
                        result.getOldestWineYear());
            }
            System.out.println("-".repeat(65));
            System.out.println("Ukupno sorti: " + results.size());
        }
    }

    public GrapeVarietyWineCountService getGrapeVarietyWineCountService() {
        return grapeVarietyWineCountService;
    }
} 