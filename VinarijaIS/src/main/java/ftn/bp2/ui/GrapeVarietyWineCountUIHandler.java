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
                    case 2:
                        displayGrapeVarietyWineCountByColor();
                        break;
                    case 3:
                        displayGrapeVarietyWineCountByName();
                        break;
                    case 4:
                        displayRedGrapeVarietyWineCount();
                        break;
                    case 5:
                        displayWhiteGrapeVarietyWineCount();
                        break;
                    case 6:
                        displayRoseGrapeVarietyWineCount();
                        break;
                    case 7:
                        displayStatistics();
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
        System.out.println("2. Pretraži po boji sorte grožđa");
        System.out.println("3. Pretraži po nazivu sorte grožđa");
        System.out.println("4. Prikaži crvene sorte grožđa");
        System.out.println("5. Prikaži bele sorte grožđa");
        System.out.println("6. Prikaži roze sorte grožđa");
        System.out.println("7. Prikaži statistike");
        System.out.println("0. Nazad");
    }

    private void displayAllGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getGrapeVarietyWineCount();

        System.out.println("\n=== BROJ VINA PO SORTI GROŽĐA ===");
        System.out.printf("%-30s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(45));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines());
            }
            System.out.println("-".repeat(45));
            System.out.println("Ukupno sorti: " + results.size());
        }
    }

    private void displayGrapeVarietyWineCountByColor() throws SQLException {
        System.out.print("Unesite boju sorte grožđa (crvena/bela/roze): ");
        String color = scanner.nextLine().trim();

        if (color.isEmpty()) {
            System.out.println("Boja ne može biti prazna.");
            return;
        }

        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getGrapeVarietyWineCountByColor(color);

        System.out.println("\n=== SORTE GROŽĐA BOJE: " + color.toUpperCase() + " ===");
        System.out.printf("%-30s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(45));

        if (results.isEmpty()) {
            System.out.println("Nema sorti grožđa sa tom bojom.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines());
            }
            System.out.println("-".repeat(45));
            System.out.println("Ukupno sorti: " + results.size());
        }
    }

    private void displayGrapeVarietyWineCountByName() throws SQLException {
        System.out.print("Unesite naziv sorte grožđa za pretragu: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Naziv ne može biti prazan.");
            return;
        }

        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getGrapeVarietyWineCountByName(name);

        System.out.println("\n=== REZULTATI PRETRAGE ZA: " + name.toUpperCase() + " ===");
        System.out.printf("%-30s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(45));

        if (results.isEmpty()) {
            System.out.println("Nema sorti grožđa sa tim nazivom.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines());
            }
            System.out.println("-".repeat(45));
            System.out.println("Ukupno rezultata: " + results.size());
        }
    }

    private void displayRedGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getRedGrapeVarietyWineCount();

        System.out.println("\n=== CRVENE SORTE GROŽĐA ===");
        System.out.printf("%-30s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(45));

        if (results.isEmpty()) {
            System.out.println("Nema crvenih sorti grožđa.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines());
            }
            System.out.println("-".repeat(45));
            System.out.println("Ukupno crvenih sorti: " + results.size());
        }
    }

    private void displayWhiteGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getWhiteGrapeVarietyWineCount();

        System.out.println("\n=== BELE SORTE GROŽĐA ===");
        System.out.printf("%-30s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(45));

        if (results.isEmpty()) {
            System.out.println("Nema belih sorti grožđa.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines());
            }
            System.out.println("-".repeat(45));
            System.out.println("Ukupno belih sorti: " + results.size());
        }
    }

    private void displayRoseGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = grapeVarietyWineCountService.getRoseGrapeVarietyWineCount();

        System.out.println("\n=== ROZE SORTE GROŽĐA ===");
        System.out.printf("%-30s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(45));

        if (results.isEmpty()) {
            System.out.println("Nema roze sorti grožđa.");
        } else {
            for (GrapeVarietyWineCountDTO result : results) {
                System.out.printf("%-30s %-15d%n",
                        result.getGrapeVariety(),
                        result.getNumberOfWines());
            }
            System.out.println("-".repeat(45));
            System.out.println("Ukupno roze sorti: " + results.size());
        }
    }

    private void displayStatistics() throws SQLException {
        System.out.println("\n=== STATISTIKE SORTI GROŽĐA ===");
        System.out.println("-".repeat(40));

        GrapeVarietyWineCountDTO mostUsed = grapeVarietyWineCountService.getMostUsedGrapeVariety();
        GrapeVarietyWineCountDTO leastUsed = grapeVarietyWineCountService.getLeastUsedGrapeVariety();
        Integer totalWines = grapeVarietyWineCountService.getTotalWineCount();
        Double averageWines = grapeVarietyWineCountService.getAverageWinesPerVariety();

        if (mostUsed != null) {
            System.out.printf("Najviše korišćena sorta: %s (%d vina)%n", 
                    mostUsed.getGrapeVariety(), mostUsed.getNumberOfWines());
        }

        if (leastUsed != null) {
            System.out.printf("Najmanje korišćena sorta: %s (%d vina)%n", 
                    leastUsed.getGrapeVariety(), leastUsed.getNumberOfWines());
        }

        System.out.printf("Ukupan broj vina: %d%n", totalWines);
        System.out.printf("Prosečan broj vina po sorti: %.2f%n", averageWines);

        // Color statistics
        List<GrapeVarietyWineCountDTO> redVarieties = grapeVarietyWineCountService.getRedGrapeVarietyWineCount();
        List<GrapeVarietyWineCountDTO> whiteVarieties = grapeVarietyWineCountService.getWhiteGrapeVarietyWineCount();
        List<GrapeVarietyWineCountDTO> roseVarieties = grapeVarietyWineCountService.getRoseGrapeVarietyWineCount();

        System.out.println("\n--- Statistike po boji ---");
        System.out.printf("Crvene sorte: %d%n", redVarieties.size());
        System.out.printf("Bele sorte: %d%n", whiteVarieties.size());
        System.out.printf("Roze sorte: %d%n", roseVarieties.size());
    }

    public GrapeVarietyWineCountService getGrapeVarietyWineCountService() {
        return grapeVarietyWineCountService;
    }
} 