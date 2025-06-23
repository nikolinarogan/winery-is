package ftn.bp2.ui;

import ftn.bp2.dto.WineSalesWithGrapeVarietiesDTO;
import ftn.bp2.service.WineSalesWithGrapeVarietiesService;
import ftn.bp2.util.InputHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class WineSalesWithGrapeVarietiesUIHandler {
    private final Scanner scanner;
    private final WineSalesWithGrapeVarietiesService wineSalesWithGrapeVarietiesService;
    private final InputHandler inputHandler;

    public WineSalesWithGrapeVarietiesUIHandler() {
        this.scanner = new Scanner(System.in);
        this.wineSalesWithGrapeVarietiesService = new WineSalesWithGrapeVarietiesService();
        this.inputHandler = new InputHandler();
    }

    public void start() {
        System.out.println("=== IZVEŠTAJ: ANALIZA PRODAJE VINA SA SORTAMA GROŽĐA ===");
        while (true) {
            displayMenu();
            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        displayAllWineSales();
                        break;
                    case 2:
                        displayWineSalesByGrapeVariety();
                        break;
                    case 3:
                        displayWineSalesByWineName();
                        break;
                    case 4:
                        displayTop5SellingWines();
                        break;
                    case 5:
                        displayTop10SellingWines();
                        break;
                    case 6:
                        displayCustomTopSellingWines();
                        break;
                    case 7:
                        displayWineSalesByRevenueRange();
                        break;
                    case 8:
                        displayHighRevenueWines();
                        break;
                    case 9:
                        displayMediumRevenueWines();
                        break;
                    case 10:
                        displayLowRevenueWines();
                        break;
                    case 11:
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
        System.out.println("\n=== MENI IZVEŠTAJA PRODAJE VINA ===");
        System.out.println("1. Prikaži sve prodaje vina");
        System.out.println("2. Pretraži po sorti grožđa");
        System.out.println("3. Pretraži po nazivu vina");
        System.out.println("4. Top 5 najprodavanijih vina");
        System.out.println("5. Top 10 najprodavanijih vina");
        System.out.println("6. Prilagođeni broj najprodavanijih vina");
        System.out.println("7. Pretraži po opsegu prihoda");
        System.out.println("8. Vina sa visokim prihodom (>1000€)");
        System.out.println("9. Vina sa srednjim prihodom (500-999€)");
        System.out.println("10. Vina sa niskim prihodom (<500€)");
        System.out.println("11. Prikaži statistike");
        System.out.println("0. Nazad");
    }

    private void displayAllWineSales() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getWineSalesWithGrapeVarieties();

        System.out.println("\n=== ANALIZA PRODAJE VINA SA SORTAMA GROŽĐA ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayWineSalesByGrapeVariety() throws SQLException {
        System.out.print("Unesite naziv sorte grožđa za pretragu: ");
        String grapeVariety = scanner.nextLine().trim();

        if (grapeVariety.isEmpty()) {
            System.out.println("Naziv sorte grožđa ne može biti prazan.");
            return;
        }

        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getWineSalesByGrapeVariety(grapeVariety);

        System.out.println("\n=== PRODAJA VINA SA SORTOM: " + grapeVariety.toUpperCase() + " ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema vina sa tom sortom grožđa.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayWineSalesByWineName() throws SQLException {
        System.out.print("Unesite naziv vina za pretragu: ");
        String wineName = scanner.nextLine().trim();

        if (wineName.isEmpty()) {
            System.out.println("Naziv vina ne može biti prazan.");
            return;
        }

        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getWineSalesByWineName(wineName);

        System.out.println("\n=== REZULTATI PRETRAGE ZA: " + wineName.toUpperCase() + " ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema vina sa tim nazivom.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno rezultata: " + results.size());
        }
    }

    private void displayTop5SellingWines() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getTop5SellingWines();

        System.out.println("\n=== TOP 5 NAJPRODAVANIJIH VINA ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                WineSalesWithGrapeVarietiesDTO result = results.get(i);
                System.out.printf("%d. %-27s %-25s %-12d %-15.2f%n",
                        i + 1,
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
        }
    }

    private void displayTop10SellingWines() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getTop10SellingWines();

        System.out.println("\n=== TOP 10 NAJPRODAVANIJIH VINA ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                WineSalesWithGrapeVarietiesDTO result = results.get(i);
                System.out.printf("%d. %-27s %-25s %-12d %-15.2f%n",
                        i + 1,
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
        }
    }

    private void displayCustomTopSellingWines() throws SQLException {
        System.out.print("Unesite broj najprodavanijih vina za prikaz (1-100): ");
        int limit = inputHandler.getIntInput("");

        if (limit <= 0 || limit > 100) {
            System.out.println("Broj mora biti između 1 i 100.");
            return;
        }

        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getTopSellingWines(limit);

        System.out.println("\n=== TOP " + limit + " NAJPRODAVANIJIH VINA ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                WineSalesWithGrapeVarietiesDTO result = results.get(i);
                System.out.printf("%d. %-27s %-25s %-12d %-15.2f%n",
                        i + 1,
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
        }
    }

    private void displayWineSalesByRevenueRange() throws SQLException {
        System.out.print("Unesite minimalni prihod (€): ");
        Double minRevenue = inputHandler.getDoubleInput("");

        System.out.print("Unesite maksimalni prihod (€): ");
        Double maxRevenue = inputHandler.getDoubleInput("");

        if (minRevenue == null || maxRevenue == null) {
            System.out.println("Prihod ne može biti prazan.");
            return;
        }

        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getWineSalesByRevenueRange(minRevenue, maxRevenue);

        System.out.println("\n=== VINA SA PRIHODOM IZMEĐU " + minRevenue + "€ I " + maxRevenue + "€ ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema vina u tom opsegu prihoda.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayHighRevenueWines() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getHighRevenueWines();

        System.out.println("\n=== VINA SA VISOKIM PRIHODOM (>1000€) ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema vina sa visokim prihodom.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayMediumRevenueWines() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getMediumRevenueWines();

        System.out.println("\n=== VINA SA SREDNJIM PRIHODOM (500-999€) ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema vina sa srednjim prihodom.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayLowRevenueWines() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = wineSalesWithGrapeVarietiesService.getLowRevenueWines();

        System.out.println("\n=== VINA SA NISKIM PRIHODOM (<500€) ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        if (results.isEmpty()) {
            System.out.println("Nema vina sa niskim prihodom.");
        } else {
            for (WineSalesWithGrapeVarietiesDTO result : results) {
                System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                        result.getNazivVina(),
                        result.getSorteGrozdja(),
                        result.getUkupnoBoca(),
                        result.getUkupanPrihod());
            }
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayStatistics() throws SQLException {
        System.out.println("\n=== STATISTIKE PRODAJE VINA ===");
        System.out.println("-".repeat(40));

        WineSalesWithGrapeVarietiesDTO bestSelling = wineSalesWithGrapeVarietiesService.getBestSellingWine();
        Double totalRevenue = wineSalesWithGrapeVarietiesService.getTotalRevenue();
        Integer totalBottles = wineSalesWithGrapeVarietiesService.getTotalBottlesSold();
        Double avgRevenue = wineSalesWithGrapeVarietiesService.getAverageRevenuePerWine();
        Double avgBottles = wineSalesWithGrapeVarietiesService.getAverageBottlesPerWine();

        if (bestSelling != null) {
            System.out.printf("Najprodavanije vino: %s (%.2f€)%n", 
                    bestSelling.getNazivVina(), bestSelling.getUkupanPrihod());
        }

        System.out.printf("Ukupan prihod: %.2f€%n", totalRevenue);
        System.out.printf("Ukupno prodatih boca: %d%n", totalBottles);
        System.out.printf("Prosečan prihod po vinu: %.2f€%n", avgRevenue);
        System.out.printf("Prosečan broj boca po vinu: %.2f%n", avgBottles);

        // Revenue category statistics
        List<WineSalesWithGrapeVarietiesDTO> highRevenue = wineSalesWithGrapeVarietiesService.getHighRevenueWines();
        List<WineSalesWithGrapeVarietiesDTO> mediumRevenue = wineSalesWithGrapeVarietiesService.getMediumRevenueWines();
        List<WineSalesWithGrapeVarietiesDTO> lowRevenue = wineSalesWithGrapeVarietiesService.getLowRevenueWines();

        System.out.println("\n--- Statistike po kategorijama prihoda ---");
        System.out.printf("Vina sa visokim prihodom: %d%n", highRevenue.size());
        System.out.printf("Vina sa srednjim prihodom: %d%n", mediumRevenue.size());
        System.out.printf("Vina sa niskim prihodom: %d%n", lowRevenue.size());
    }

    public WineSalesWithGrapeVarietiesService getWineSalesWithGrapeVarietiesService() {
        return wineSalesWithGrapeVarietiesService;
    }
} 