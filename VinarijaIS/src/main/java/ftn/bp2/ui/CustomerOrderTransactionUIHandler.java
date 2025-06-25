package ftn.bp2.ui;

import ftn.bp2.dto.BottleInfoDTO;
import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;
import ftn.bp2.service.CustomerOrderTransactionService;
import ftn.bp2.util.InputHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerOrderTransactionUIHandler {
    private final Scanner scanner;
    private final CustomerOrderTransactionService customerOrderTransactionService;
    private final InputHandler inputHandler;

    public CustomerOrderTransactionUIHandler() {
        this.scanner = new Scanner(System.in);
        this.customerOrderTransactionService = new CustomerOrderTransactionService();
        this.inputHandler = new InputHandler();
    }

    public void start() {
        System.out.println("=== TRANSAKCIJA: KOMPLETNA NARUDŽBA KUPCA ===");
        while (true) {
            displayMenu();
            int choice = inputHandler.getIntInput("Izaberite opciju: ");

            try {
                switch (choice) {
                    case 1:
                        executeCustomerOrderTransaction();
                        break;
                    case 2:
                        displayAvailableBottles();
                        break;
                    case 3:
                        displayBottlesByWine();
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
        System.out.println("\n=== MENI TRANSAKCIJA ===");
        System.out.println("1. Izvrši kompletnu narudžbu kupca");
        System.out.println("2. Prikaži dostupne boce");
        System.out.println("3. Prikaži boce po vinu");
        System.out.println("0. Nazad");
    }

    private void executeCustomerOrderTransaction() throws SQLException {
        System.out.println("\n=== KOMPLETNA NARUDŽBA KUPCA ===");
        System.out.println("Unesite podatke za transakciju:");
        System.out.println("NAPOMENA: Email kupca mora biti jedinstven!");

        // Get customer information
        System.out.print("Email kupca: ");
        String email = scanner.nextLine().trim();

        // Check if customer already exists
        boolean customerExists = customerOrderTransactionService.validateCustomerEmailExists(email);
        if (customerExists) {
            System.out.println("Kupac sa email-om " + email + " već postoji!");
            System.out.println("Email mora biti jedinstven. Pokušajte sa drugim email-om.");
            
            Integer existingCustomerId = customerOrderTransactionService.getCustomerIdByEmail(email);
            if (existingCustomerId != null) {
                System.out.println("ID postojećeg kupca: " + existingCustomerId);
            }
            return;
        }

        System.out.print("Broj telefona: ");
        String phoneNumber = scanner.nextLine().trim();

        // Get payment method
        System.out.println("Metod plaćanja:");
        System.out.println("1. Gotovinsko placanje");
        System.out.println("2. Karticno placanje");
        int paymentChoice = inputHandler.getIntInput("Izaberite metod plaćanja: ");
        
        String paymentMethod;
        switch (paymentChoice) {
            case 1: paymentMethod = "gotovinsko placanje"; break;
            case 2: paymentMethod = "karticno placanje"; break;
            default: 
                System.out.println("Nevažeći izbor. Koristi se gotovina.");
                paymentMethod = "gotovinsko placanje";
        }

        // Display available bottles
        System.out.println("\n=== DOSTUPNE BOCE ===");
        List<BottleInfoDTO> availableBottles = customerOrderTransactionService.getAvailableBottles();
        if (availableBottles.isEmpty()) {
            System.out.println("Nema dostupnih boca za kupovinu!");
            return;
        }

        displayBottleList(availableBottles);

        // Get bottle selections
        List<Integer> selectedBottles = getBottleSelections(availableBottles);
        if (selectedBottles.isEmpty()) {
            System.out.println("Nijedna boca nije izabrana. Transakcija otkazana.");
            return;
        }

        System.out.println("\nIzvršavam transakciju...");
        TransactionResultDTO result = customerOrderTransactionService.executeCustomerOrderTransaction(
                email, phoneNumber, paymentMethod, selectedBottles);

        displayTransactionResult(result);
    }

    private void displayAvailableBottles() throws SQLException {
        System.out.println("\n=== DOSTUPNE BOCE ===");
        List<BottleInfoDTO> bottles = customerOrderTransactionService.getAvailableBottles();
        
        if (bottles.isEmpty()) {
            System.out.println("Nema dostupnih boca.");
        } else {
            displayBottleList(bottles);
        }
    }

    private void displayBottlesByWine() throws SQLException {
        System.out.println("\n=== BOCE PO VINU ===");
        System.out.print("Unesite ID vina: ");
        Integer wineId = inputHandler.getIntInput("");
        
        if (wineId == null || wineId <= 0) {
            System.out.println("Nevažeći ID vina.");
            return;
        }

        try {
            List<BottleInfoDTO> bottles = customerOrderTransactionService.getBottlesByWineId(wineId);
            
            if (bottles.isEmpty()) {
                System.out.println("Nema dostupnih boca za ovo vino.");
            } else {
                System.out.println("Dostupne boce za vino ID " + wineId + ":");
                displayBottleList(bottles);
            }
        } catch (SQLException e) {
            System.out.println("Greška: " + e.getMessage());
        }
    }

    private void displayBottleList(List<BottleInfoDTO> bottles) {
        System.out.printf("%-8s %-8s %-8s %-30s %-12s%n", 
                         "SerBr", "Kapacitet", "ID Vina", "Naziv Vina", "Status");
        System.out.println("-".repeat(70));
        
        for (BottleInfoDTO bottle : bottles) {
            String status = bottle.isAvailable() ? "Dostupna" : "Prodana";
            System.out.printf("%-8d %-8.2f %-8d %-30s %-12s%n",
                             bottle.getSerialNumber(),
                             bottle.getCapacity(),
                             bottle.getWineId(),
                             bottle.getWineName() != null ? bottle.getWineName() : "N/A",
                             status);
        }
    }

    private List<Integer> getBottleSelections(List<BottleInfoDTO> availableBottles) {
        List<Integer> selectedBottles = new ArrayList<>();
        
        System.out.println("\n=== IZBOR BOCA ===");
        System.out.println("Unesite serijske brojeve boca koje želite kupiti (0 za kraj):");
        
        while (true) {
            System.out.print("Serijski broj boce: ");
            Integer serialNumber = inputHandler.getIntInput("");
            
            if (serialNumber == null || serialNumber == 0) {
                break;
            }
            
            // Check if bottle exists and is available
            boolean bottleExists = availableBottles.stream()
                .anyMatch(b -> b.getSerialNumber().equals(serialNumber) && b.isAvailable());
            
            if (!bottleExists) {
                System.out.println("Boca sa serijskim brojem " + serialNumber + " nije dostupna!");
                continue;
            }
            
            if (selectedBottles.contains(serialNumber)) {
                System.out.println("Boca " + serialNumber + " je već izabrana!");
                continue;
            }
            
            selectedBottles.add(serialNumber);
            System.out.println("Boca " + serialNumber + " dodana u korpu.");
        }
        
        return selectedBottles;
    }

    private void displayTransactionResult(TransactionResultDTO result) {
        System.out.println("\n=== REZULTAT TRANSAKCIJE ===");
        
        if (result.isSuccess()) {
            System.out.println("Transakcija uspešno izvršena!");
            System.out.println("Poruka: " + result.getMessage());
            
            if (result.getCustomerId() != null) {
                System.out.println("ID kupca: " + result.getCustomerId());
            }
            
            if (result.getOrderId() != null) {
                System.out.println("ID narudžbe: " + result.getOrderId());
            }
        } else {
            System.out.println("Transakcija neuspešna!");
            System.out.println("Greška: " + result.getError());
            if (result.getMessage() != null) {
                System.out.println("Poruka: " + result.getMessage());
            }
        }
    }

    public CustomerOrderTransactionService getCustomerOrderTransactionService() {
        return customerOrderTransactionService;
    }
} 