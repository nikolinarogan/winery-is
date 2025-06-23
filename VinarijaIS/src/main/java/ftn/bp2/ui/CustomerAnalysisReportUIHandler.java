package ftn.bp2.ui;

import ftn.bp2.dto.CustomerAnalysisReportDTO;
import ftn.bp2.service.CustomerAnalysisReportService;
import ftn.bp2.util.InputHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerAnalysisReportUIHandler {
    private final Scanner scanner;
    private final CustomerAnalysisReportService customerAnalysisReportService;
    private final InputHandler inputHandler;

    public CustomerAnalysisReportUIHandler() {
        this.scanner = new Scanner(System.in);
        this.customerAnalysisReportService = new CustomerAnalysisReportService();
        this.inputHandler = new InputHandler();
    }

    public void start() {
        System.out.println("=== IZVEŠTAJ: ANALIZA KUPACA I PREFERENCIJA ===");
        while (true) {
            displayMenu();
            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        displayAllCustomerAnalysis();
                        break;
                    case 2:
                        displayCustomerAnalysisByEmail();
                        break;
                    case 3:
                        displayCustomerAnalysisByGrapeVariety();
                        break;
                    case 4:
                        displayTop5Customers();
                        break;
                    case 5:
                        displayTop10Customers();
                        break;
                    case 6:
                        displayCustomTopCustomers();
                        break;
                    case 7:
                        displayHighValueCustomers();
                        break;
                    case 8:
                        displayMediumValueCustomers();
                        break;
                    case 9:
                        displayLowValueCustomers();
                        break;
                    case 10:
                        displayWineEnthusiasts();
                        break;
                    case 11:
                        displayCustomersByOrderCount();
                        break;
                    case 12:
                        displayCustomersByWineCount();
                        break;
                    case 13:
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
        System.out.println("\n=== MENI IZVEŠTAJA KUPACA ===");
        System.out.println("1. Prikaži sve kupce");
        System.out.println("2. Pretraži po email adresi");
        System.out.println("3. Pretraži po sorti grožđa");
        System.out.println("4. Top 5 kupaca");
        System.out.println("5. Top 10 kupaca");
        System.out.println("6. Prilagođeni broj top kupaca");
        System.out.println("7. Kupci sa visokom vrednošću (5+ narudžbi)");
        System.out.println("8. Kupci sa srednjom vrednošću (2-4 narudžbe)");
        System.out.println("9. Kupci sa niskom vrednošću (1 narudžba)");
        System.out.println("10. Entuzijasti vina (3+ različitih vina)");
        System.out.println("11. Pretraži po broju narudžbi");
        System.out.println("12. Pretraži po broju različitih vina");
        System.out.println("13. Prikaži statistike");
        System.out.println("0. Nazad");
    }

    private void displayAllCustomerAnalysis() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getCustomerAnalysisReport();

        System.out.println("\n=== ANALIZA KUPACA I PREFERENCIJA ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayCustomerAnalysisByEmail() throws SQLException {
        System.out.print("Unesite email adresu za pretragu: ");
        String email = scanner.nextLine().trim();

        if (email.isEmpty()) {
            System.out.println("Email ne može biti prazan.");
            return;
        }

        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getCustomerAnalysisByEmail(email);

        System.out.println("\n=== REZULTATI PRETRAGE ZA: " + email.toUpperCase() + " ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca sa tim email-om.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno rezultata: " + results.size());
        }
    }

    private void displayCustomerAnalysisByGrapeVariety() throws SQLException {
        System.out.print("Unesite sortu grožđa za pretragu: ");
        String grapeVariety = scanner.nextLine().trim();

        if (grapeVariety.isEmpty()) {
            System.out.println("Naziv sorte grožđa ne može biti prazan.");
            return;
        }

        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getCustomerAnalysisByGrapeVariety(grapeVariety);

        System.out.println("\n=== KUPCI KOJI VOLI " + grapeVariety.toUpperCase() + " ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca koji vole tu sortu grožđa.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayTop5Customers() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getTop5Customers();

        System.out.println("\n=== TOP 5 KUPACA ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                CustomerAnalysisReportDTO result = results.get(i);
                System.out.printf("%d. %-27s %-15d %-30s %-15d%n",
                        i + 1,
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
        }
    }

    private void displayTop10Customers() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getTop10Customers();

        System.out.println("\n=== TOP 10 KUPACA ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                CustomerAnalysisReportDTO result = results.get(i);
                System.out.printf("%d. %-27s %-15d %-30s %-15d%n",
                        i + 1,
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
        }
    }

    private void displayCustomTopCustomers() throws SQLException {
        System.out.print("Unesite broj top kupaca za prikaz (1-100): ");
        int limit = inputHandler.getIntInput("");

        if (limit <= 0 || limit > 100) {
            System.out.println("Broj mora biti između 1 i 100.");
            return;
        }

        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getTopCustomers(limit);

        System.out.println("\n=== TOP " + limit + " KUPACA ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                CustomerAnalysisReportDTO result = results.get(i);
                System.out.printf("%d. %-27s %-15d %-30s %-15d%n",
                        i + 1,
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
        }
    }

    private void displayHighValueCustomers() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getHighValueCustomers();

        System.out.println("\n=== KUPCI SA VISOKOM VREDNOŠĆU (5+ NARUDŽBI) ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca sa visokom vrednošću.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayMediumValueCustomers() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getMediumValueCustomers();

        System.out.println("\n=== KUPCI SA SREDNJOM VREDNOŠĆU (2-4 NARUDŽBE) ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca sa srednjom vrednošću.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayLowValueCustomers() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getLowValueCustomers();

        System.out.println("\n=== KUPCI SA NISKOM VREDNOŠĆU (1 NARUDŽBA) ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca sa niskom vrednošću.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayWineEnthusiasts() throws SQLException {
        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getWineEnthusiasts();

        System.out.println("\n=== ENTUZIJASTI VINA (3+ RAZLIČITIH VINA) ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema entuzijasta vina.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno entuzijasta: " + results.size());
        }
    }

    private void displayCustomersByOrderCount() throws SQLException {
        System.out.print("Unesite minimalni broj narudžbi: ");
        int minOrders = inputHandler.getIntInput("");

        if (minOrders < 0) {
            System.out.println("Broj narudžbi ne može biti negativan.");
            return;
        }

        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getCustomerAnalysisByOrderCount(minOrders);

        System.out.println("\n=== KUPCI SA " + minOrders + "+ NARUDŽBI ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca sa tim brojem narudžbi.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayCustomersByWineCount() throws SQLException {
        System.out.print("Unesite minimalni broj različitih vina: ");
        int minWines = inputHandler.getIntInput("");

        if (minWines < 0) {
            System.out.println("Broj vina ne može biti negativan.");
            return;
        }

        List<CustomerAnalysisReportDTO> results = customerAnalysisReportService.getCustomerAnalysisByWineCount(minWines);

        System.out.println("\n=== KUPCI SA " + minWines + "+ RAZLIČITIH VINA ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email", "Različita Vina", "Omiljene Sorte", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        if (results.isEmpty()) {
            System.out.println("Nema kupaca sa tim brojem različitih vina.");
        } else {
            for (CustomerAnalysisReportDTO result : results) {
                System.out.printf("%-30s %-15d %-30s %-15d%n",
                        result.getEmail(),
                        result.getRazlicitaVina(),
                        result.getOmiljeneSorte(),
                        result.getBrojNarduzbi());
            }
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());
        }
    }

    private void displayStatistics() throws SQLException {
        System.out.println("\n=== STATISTIKE KUPACA ===");
        System.out.println("-".repeat(40));

        CustomerAnalysisReportDTO bestCustomer = customerAnalysisReportService.getBestCustomer();
        Integer totalCustomers = customerAnalysisReportService.getTotalCustomers();
        Integer totalOrders = customerAnalysisReportService.getTotalOrders();
        Integer totalUniqueWines = customerAnalysisReportService.getTotalUniqueWines();
        Double avgOrders = customerAnalysisReportService.getAverageOrdersPerCustomer();
        Double avgWines = customerAnalysisReportService.getAverageWinesPerCustomer();

        if (bestCustomer != null) {
            System.out.printf("Najbolji kupac: %s (%d narudžbi)%n", 
                    bestCustomer.getEmail(), bestCustomer.getBrojNarduzbi());
        }

        System.out.printf("Ukupan broj kupaca: %d%n", totalCustomers);
        System.out.printf("Ukupan broj narudžbi: %d%n", totalOrders);
        System.out.printf("Ukupan broj različitih vina: %d%n", totalUniqueWines);
        System.out.printf("Prosečan broj narudžbi po kupcu: %.2f%n", avgOrders);
        System.out.printf("Prosečan broj vina po kupcu: %.2f%n", avgWines);

        // Customer category statistics
        List<CustomerAnalysisReportDTO> highValue = customerAnalysisReportService.getHighValueCustomers();
        List<CustomerAnalysisReportDTO> mediumValue = customerAnalysisReportService.getMediumValueCustomers();
        List<CustomerAnalysisReportDTO> lowValue = customerAnalysisReportService.getLowValueCustomers();
        List<CustomerAnalysisReportDTO> enthusiasts = customerAnalysisReportService.getWineEnthusiasts();

        System.out.println("\n--- Statistike po kategorijama kupaca ---");
        System.out.printf("Kupci sa visokom vrednošću: %d%n", highValue.size());
        System.out.printf("Kupci sa srednjom vrednošću: %d%n", mediumValue.size());
        System.out.printf("Kupci sa niskom vrednošću: %d%n", lowValue.size());
        System.out.printf("Entuzijasti vina: %d%n", enthusiasts.size());
    }

    public CustomerAnalysisReportService getCustomerAnalysisReportService() {
        return customerAnalysisReportService;
    }
} 