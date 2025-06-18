package ftn.bp2.ui;

import ftn.bp2.service.ReportService;
import ftn.bp2.service.VinogradService;
import ftn.bp2.dto.VinogradDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VinarijaUIHandler {

    private final Scanner scanner;
    private final VinogradService vinogradService;
    private final ReportService reportService;

    public VinarijaUIHandler() {
        this.scanner = new Scanner(System.in);
        this.vinogradService = new VinogradService();
        this.reportService = new ReportService();
    }

    public void start() {
        System.out.println("=== DOBRODOŠLI U SISTEM VINARIJE ===");
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        handleVinogradOperations();
                        break;
                    case 2:
                        handleReportOperations();
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

    private void handleVinogradOperations() throws Exception {
        while (true) {
            System.out.println("\n=== OPERACIJE SA VINOGRADIMA ===");
            System.out.println("1. Prikaži sve vinograde");
            System.out.println("2. Pronađi vinograd po ID");
            System.out.println("3. Dodaj novi vinograd");
            System.out.println("4. Ažuriraj vinograd");
            System.out.println("5. Obriši vinograd");
            System.out.println("6. Prikaži podređene vinograde");
            System.out.println("0. Nazad");

            int choice = getIntInput("Izaberite opciju: ");

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
                case 6:
                    displayChildVinogradi();
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
            System.out.println("2. Analiza berbi i zaposlenih po vinogradu");
            System.out.println("3. Analiza performansi zaposlenih");
            System.out.println("4. Analiza porudžbina sa detaljima");
            System.out.println("0. Nazad");

            int choice = getIntInput("Izaberite opciju: ");

            switch (choice) {
                case 1:
                    try {
                        displayGrapeVarietyWineCountReport();
                    } catch (Exception e) {
                        System.err.println("Greška prilikom generisanja izveštaja: " + e.getMessage());
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
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
        int id = getIntInput("Unesite ID vinograda: ");
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

        Float povrsina = getFloatInput("Površina: ");

        System.out.print("Datum osnivanja (YYYY-MM-DD): ");
        String datumStr = scanner.nextLine();
        LocalDate datumOsnivanja = LocalDate.parse(datumStr);

        Float kapacitet = getFloatInput("Kapacitet: ");

        Integer parentId = getOptionalIntInput("Parent vinograd ID (0 za glavni vinograd): ");
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
        int id = getIntInput("Unesite ID vinograda za ažuriranje: ");
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

        Float povrsina = getOptionalFloatInput("Nova površina (Enter za zadržavanje): ");
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
        int id = getIntInput("Unesite ID vinograda za brisanje: ");

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

    private void displayChildVinogradi() throws Exception {
        int parentId = getIntInput("Unesite ID roditeljskog vinograda: ");

        try {
            List<VinogradDTO> childVinogradi = vinogradService.getVinogradiByParentId(parentId);

            System.out.println("\n=== PODREĐENI VINOGRADI ===");
            if (childVinogradi.isEmpty()) {
                System.out.println("Nema podređenih vinograda.");
            } else {
                for (VinogradDTO vinograd : childVinogradi) {
                    System.out.printf("ID: %d | Naziv: %s | Površina: %.2f | Kapacitet: %.2f%n",
                            vinograd.getIdV(), vinograd.getImeV(), vinograd.getPoV(), vinograd.getVKap());
                }
            }
        } catch (SQLException e) {
            System.out.println("Greška pri pristupu bazi podataka: " + e.getMessage());
            System.out.println("Proverite da li vinograd sa ID " + parentId + " postoji.");
        } catch (Exception e) {
            System.out.println("Greška: " + e.getMessage());
        }
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
