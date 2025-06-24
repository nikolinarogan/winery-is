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
        System.out.println("1. Prikaži sve prodaje vina i analize");
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


    public WineSalesWithGrapeVarietiesService getWineSalesWithGrapeVarietiesService() {
        return wineSalesWithGrapeVarietiesService;
    }
} 