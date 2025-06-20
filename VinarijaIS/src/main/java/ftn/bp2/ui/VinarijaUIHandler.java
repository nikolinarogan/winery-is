package ftn.bp2.ui;

import ftn.bp2.service.ReportService;
import ftn.bp2.service.TransactionService;
import ftn.bp2.service.VinogradService;
import ftn.bp2.dto.VinogradDTO;
import ftn.bp2.util.InputHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VinarijaUIHandler {

    private final Scanner scanner;
    private final VinogradService vinogradService;
    private final ReportService reportService;
    private final TransactionService transactionService;
    private final InputHandler inputHandler;

    public VinarijaUIHandler() {
        this.scanner = new Scanner(System.in);
        this.vinogradService = new VinogradService();
        this.reportService = new ReportService();
        this.transactionService = new TransactionService();
        this.inputHandler = new InputHandler();
    }

    public void start() {
        System.out.println("=== DOBRODOŠLI U SISTEM VINARIJE ===");
        while (true) {
            displayMainMenu();
            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        handleVinogradOperations();
                        break;
                    case 2:
                        handleReportOperations();
                        break;
                    case 3:
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

    private void handleVinogradOperations() throws Exception {
        while (true) {
            System.out.println("\n=== OPERACIJE SA VINOGRADIMA ===");
            System.out.println("1. Prikaži sve vinograde");
            System.out.println("2. Pronađi vinograd po ID");
            System.out.println("3. Dodaj novi vinograd");
            System.out.println("4. Ažuriraj vinograd");
            System.out.println("5. Obriši vinograd");
            System.out.println("0. Nazad");

            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            switch (choice) {
                case 1:
                    displayAllVinogradi();
                    break;
                case 2:
                    findVinogradById();
                    break;
                case 3:
                    addNewVinograd();
                    break;
                case 4:
                    updateVinograd();
                    break;
                case 5:
                    deleteVinograd();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Nevažeća opcija.");
            }
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
                        displayGrapeVarietyWineCountReport();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom generisanja izveštaja: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        displayWineSalesWithGrapeVarieties();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom generisanja izveštaja: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        displayCustomerAnalysisReport();
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
            System.out.println("1. Kompletna narudžba kupca (novi kupac + narudžba + boca)");
            System.out.println("0. Nazad");

            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            switch (choice) {
                case 1:
                    handleCustomerOrderTransaction();
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
        System.out.println("1. Operacije sa vinogradima");
        System.out.println("2. Izvještaji");
        System.out.println("3. Transakcije");
        System.out.println("0. Izlaz");
    }

    private void displayGrapeVarietyWineCountReport() throws Exception {
        List<Map<String, Object>> results = reportService.getGrapeVarietyWineCount();

        System.out.println("\n=== BROJ VINA PO SORTI GROŽĐA ===");
        System.out.printf("%-25s %-15s%n", "Sorta grožđa", "Broj vina");
        System.out.println("-".repeat(40));

        for (Map<String, Object> row : results) {
            System.out.printf("%-25s %-15d%n",
                    row.get("grapeVariety"),
                    row.get("numberOfWines"));
        }
    }

    private void displayWineSalesWithGrapeVarieties() throws Exception {
        List<Map<String, Object>> results = reportService.getWineSalesWithGrapeVarieties();

        System.out.println("\n=== ANALIZA PRODAJE VINA SA SORTAMA GROŽĐA ===");
        System.out.printf("%-30s %-25s %-12s %-15s%n",
                "Naziv Vina", "Sorte Grožđa", "Ukupno Boca", "Ukupan Prihod (€)");
        System.out.println("-".repeat(85));

        for (Map<String, Object> row : results) {
            System.out.printf("%-30s %-25s %-12d %-15.2f%n",
                    row.get("nazivVina"),
                    row.get("sorteGrozdja"),
                    row.get("ukupnoBoca"),
                    row.get("ukupanPrihod"));
        }

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            System.out.println("-".repeat(85));
            System.out.println("Ukupno vina: " + results.size());
        }
    }

    private void displayCustomerAnalysisReport() throws Exception {
        List<Map<String, Object>> results = reportService.getCustomerAnalysisReport();

        System.out.println("\n=== ANALIZA KUPACA I PREFERENCIJA ===");
        System.out.printf("%-30s %-15s %-30s %-15s%n",
                "Email Kupca", "Različita Vina", "Omiljene Sorte Grožđa", "Broj Narudžbi");
        System.out.println("-".repeat(95));

        for (Map<String, Object> row : results) {
            System.out.printf("%-30s %-15d %-30s %-15d%n",
                    row.get("email"),
                    row.get("razlicitaVina"),
                    row.get("omiljeneSorte"),
                    row.get("brojNarduzbi"));
        }

        if (results.isEmpty()) {
            System.out.println("Nema podataka za prikaz.");
        } else {
            System.out.println("-".repeat(95));
            System.out.println("Ukupno kupaca: " + results.size());

            // Calculate summary statistics
            int totalOrders = results.stream()
                    .mapToInt(row -> (Integer) row.get("brojNarduzbi"))
                    .sum();

            double avgWinesPerCustomer = results.stream()
                    .mapToDouble(row -> (Integer) row.get("razlicitaVina"))
                    .average()
                    .orElse(0.0);

            double avgOrdersPerCustomer = results.stream()
                    .mapToDouble(row -> (Integer) row.get("brojNarduzbi"))
                    .average()
                    .orElse(0.0);

            System.out.printf("Ukupan broj narudžbi: %d%n", totalOrders);
            System.out.printf("Prosečan broj različitih vina po kupcu: %.1f%n", avgWinesPerCustomer);
            System.out.printf("Prosečan broj narudžbi po kupcu: %.1f%n", avgOrdersPerCustomer);
        }
    }
    private void handleCustomerOrderTransaction() throws Exception {
        System.out.println("\n=== KOMPLETNA NARUDŽBA KUPCA ===");
        System.out.println("Ova transakcija će kreirati novog kupca, narudžbu i dodati bocu vina.");

        // Get customer information
        String email = inputHandler.getStringInput("Unesite email kupca: ");
        String phoneNumber = inputHandler.getStringInput("Unesite broj telefona kupca: ");
        String paymentMethod = inputHandler.getStringInput("Unesite način plaćanja (kartica/kes/ček): ");
        Integer wineId = inputHandler.getIntInput("Unesite ID vina: ");
        Float bottleCapacity = inputHandler.getFloatInput("Unesite zapreminu boce (L): ");

        // Validate inputs
        if (email == null || phoneNumber == null || paymentMethod == null ||
                wineId == null || bottleCapacity == null) {
            System.out.println("Greška: Svi podaci su obavezni.");
            return;
        }

        if (bottleCapacity <= 0 || bottleCapacity > 10.0f) {
            System.out.println("Greška: Zapremina boce mora biti između 0 i 10 litara.");
            return;
        }

        if (!paymentMethod.matches("kartica|kes|ček")) {
            System.out.println("Greška: Način plaćanja mora biti 'kartica', 'kes' ili 'ček'.");
            return;
        }

        try {
            Map<String, Object> result = transactionService.executeCustomerOrderTransaction(
                    email, phoneNumber, paymentMethod, wineId, bottleCapacity);

            if ((Boolean) result.get("success")) {
                System.out.println("✓ Transakcija uspešno izvršena!");
                System.out.println("  - Novi kupac kreiran sa ID: " + result.get("customerId"));
                System.out.println("  - Nova narudžba kreirana sa ID: " + result.get("orderId"));
                System.out.println("  - Boca vina dodana u narudžbu");
                System.out.println("  - Poruka: " + result.get("message"));
            } else {
                System.out.println("✗ Transakcija nije uspešna.");
                System.out.println("  - Greška: " + result.get("error"));
            }
        } catch (SQLException e) {
            System.out.println("✗ Greška pri izvršavanju transakcije: " + e.getMessage());
            if (e.getMessage().contains("violates foreign key constraint")) {
                System.out.println("  - Proverite da li ID vina postoji u bazi podataka.");
            }
        }
    }
    // Vinograd operations
    private void displayAllVinogradi() throws Exception {
        List<VinogradDTO> vinogradi = vinogradService.getAllVinogradi();
        System.out.println("\n=== SVI VINOGRADI ===");
        if (vinogradi.isEmpty()) {
            System.out.println("Nema vinograda u bazi.");
        } else {
            for (VinogradDTO vinograd : vinogradi) {
                System.out.printf("ID: %d | Naziv: %s | Površina: %.2f | Kapacitet: %.2f%n",
                        vinograd.getIdV(), vinograd.getImeV(), vinograd.getPoV(), vinograd.getVKap());
            }
        }
    }

    private void findVinogradById() throws Exception {
        int id = inputHandler.getIntInput("Unesite ID vinograda: ");
        VinogradDTO vinograd = vinogradService.getVinogradById(id);

        if (vinograd != null) {
            System.out.println("\n=== DETALJI VINOGRADA ===");
            System.out.printf("ID: %d%n", vinograd.getIdV());
            System.out.printf("Naziv: %s%n", vinograd.getImeV());
            System.out.printf("Površina: %.2f%n", vinograd.getPoV());
            System.out.printf("Datum osnivanja: %s%n", vinograd.getDatOsn());
            System.out.printf("Kapacitet: %.2f%n", vinograd.getVKap());
        } else {
            System.out.println("Vinograd sa ID " + id + " nije pronađen.");
        }
    }

    private void addNewVinograd() throws Exception {
        System.out.println("\n=== DODAVANJE NOVOG VINOGRADA ===");

        System.out.print("Naziv: ");
        String naziv = scanner.nextLine();

        Float povrsina = inputHandler.getFloatInput("Površina: ");

        System.out.print("Datum osnivanja (YYYY-MM-DD): ");
        String datumStr = scanner.nextLine();
        LocalDate datumOsnivanja = LocalDate.parse(datumStr);

        Float kapacitet = inputHandler.getFloatInput("Kapacitet: ");

        Integer parentId = inputHandler.getOptionalIntInput("Parent vinograd ID (0 za glavni vinograd): ");
        if (parentId != null && parentId == 0) {
            parentId = null;
        }

        VinogradDTO vinograd = new VinogradDTO();
        vinograd.setImeV(naziv);
        vinograd.setPoV(povrsina);
        vinograd.setDatOsn(datumOsnivanja);
        vinograd.setVKap(kapacitet);
        vinograd.setVinogradIdV(parentId);

        Integer newId = vinogradService.createVinograd(vinograd);
        if (newId != null) {
            System.out.println("Vinograd uspešno kreiran sa ID: " + newId);
        } else {
            System.out.println("Greška pri kreiranju vinograda.");
        }
    }

    private void updateVinograd() throws Exception {
        int id = inputHandler.getIntInput("Unesite ID vinograda za ažuriranje: ");
        VinogradDTO vinograd = vinogradService.getVinogradById(id);

        if (vinograd == null) {
            System.out.println("Vinograd sa ID " + id + " nije pronađen.");
            return;
        }

        System.out.println("\n=== AŽURIRANJE VINOGRADA ===");
        System.out.printf("Trenutni naziv: %s%n", vinograd.getImeV());
        System.out.print("Novi naziv (Enter za zadržavanje): ");
        String naziv = scanner.nextLine();
        if (!naziv.trim().isEmpty()) {
            vinograd.setImeV(naziv);
        }

        Float povrsina = inputHandler.getOptionalFloatInput("Nova površina (Enter za zadržavanje): ");
        if (povrsina != null) {
            vinograd.setPoV(povrsina);
        }

        boolean updated = vinogradService.updateVinograd(vinograd);
        if (updated) {
            System.out.println("Vinograd uspešno ažuriran.");
        } else {
            System.out.println("Greška pri ažuriranju vinograda.");
        }
    }

    private void deleteVinograd() throws Exception {
        int id = inputHandler.getIntInput("Unesite ID vinograda za brisanje: ");

        System.out.print("Da li ste sigurni? (da/ne): ");
        String confirm = scanner.nextLine();

        if ("da".equalsIgnoreCase(confirm)) {
            try {
                boolean deleted = vinogradService.deleteVinograd(id);
                if (deleted) {
                    System.out.println("Vinograd uspešno obrisan.");
                } else {
                    System.out.println("Vinograd sa ID " + id + " nije pronađen ili već obrisan.");
                }
            } catch (SQLException e) {
                System.out.println("Greška pri brisanju vinograda: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Greška: " + e.getMessage());
            }
        } else {
            System.out.println("Brisanje otkazano.");
        }
    }
}
