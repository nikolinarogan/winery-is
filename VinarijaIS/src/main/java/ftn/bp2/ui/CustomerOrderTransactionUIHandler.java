package ftn.bp2.ui;

import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;
import ftn.bp2.service.CustomerOrderTransactionService;
import ftn.bp2.util.InputHandler;

import java.sql.SQLException;
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
                        validateTransactionData();
                        break;
                    case 3:
                        checkWineExists();
                        break;
                    case 4:
                        checkCustomerExists();
                        break;
                    case 5:
                        getCustomerIdByEmail();
                        break;
                    case 6:
                        displayTransactionHelp();
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
        System.out.println("2. Validiraj podatke transakcije");
        System.out.println("3. Proveri da li vino postoji");
        System.out.println("4. Proveri da li kupac postoji");
        System.out.println("5. Pronađi ID kupca po email-u");
        System.out.println("6. Pomoć za transakcije");
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
            System.out.println("❌ Kupac sa email-om " + email + " već postoji!");
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
        System.out.println("1. Gotovina (cash)");
        System.out.println("2. Kartica (card)");
        System.out.println("3. Transfer (transfer)");
        int paymentChoice = inputHandler.getIntInput("Izaberite metod plaćanja (1-3): ");
        
        String paymentMethod;
        switch (paymentChoice) {
            case 1: paymentMethod = "cash"; break;
            case 2: paymentMethod = "card"; break;
            case 3: paymentMethod = "transfer"; break;
            default: 
                System.out.println("Nevažeći izbor. Koristi se gotovina.");
                paymentMethod = "cash";
        }

        // Get wine information
        System.out.print("ID vina: ");
        Integer wineId = inputHandler.getIntInput("");

        System.out.print("Kapacitet boce (litri): ");
        Float bottleCapacity = inputHandler.getFloatInput("");

        // Execute transaction
        System.out.println("\nIzvršavam transakciju...");
        TransactionResultDTO result = customerOrderTransactionService.executeCustomerOrderTransaction(
                email, phoneNumber, paymentMethod, wineId, bottleCapacity);

        // Display result
        displayTransactionResult(result);
    }

    private void validateTransactionData() {
        System.out.println("\n=== VALIDACIJA PODATAKA TRANSAKCIJE ===");
        System.out.println("Unesite podatke za validaciju:");

        System.out.print("Email kupca: ");
        String email = scanner.nextLine().trim();

        // Check email uniqueness
        try {
            boolean customerExists = customerOrderTransactionService.validateCustomerEmailExists(email);
            if (customerExists) {
                System.out.println("❌ Email već postoji u bazi!");
                System.out.println("Email mora biti jedinstven.");
                return;
            } else {
                System.out.println("✅ Email je dostupan (jedinstven).");
            }
        } catch (SQLException e) {
            System.out.println("❌ Greška pri proveri email-a: " + e.getMessage());
            return;
        }

        System.out.print("Broj telefona: ");
        String phoneNumber = scanner.nextLine().trim();

        System.out.println("Metod plaćanja:");
        System.out.println("1. Gotovina (cash)");
        System.out.println("2. Kartica (card)");
        System.out.println("3. Transfer (transfer)");
        int paymentChoice = inputHandler.getIntInput("Izaberite metod plaćanja (1-3): ");
        
        String paymentMethod;
        switch (paymentChoice) {
            case 1: paymentMethod = "cash"; break;
            case 2: paymentMethod = "card"; break;
            case 3: paymentMethod = "transfer"; break;
            default: 
                System.out.println("Nevažeći izbor. Koristi se gotovina.");
                paymentMethod = "cash";
        }

        System.out.print("ID vina: ");
        Integer wineId = inputHandler.getIntInput("");

        System.out.print("Kapacitet boce (litri): ");
        Float bottleCapacity = inputHandler.getFloatInput("");

        // Validate data
        TransactionResultDTO validationResult = customerOrderTransactionService.validateTransactionData(
                email, phoneNumber, paymentMethod, wineId, bottleCapacity);

        System.out.println("\n=== REZULTAT VALIDACIJE ===");
        if (validationResult.isSuccess()) {
            System.out.println("✅ Validacija uspešna!");
            System.out.println("Poruka: " + validationResult.getMessage());
        } else {
            System.out.println("❌ Validacija neuspešna!");
            System.out.println("Greška: " + validationResult.getError());
        }
    }

    private void checkWineExists() throws SQLException {
        System.out.println("\n=== PROVERA POSTOJANJA VINA ===");
        System.out.print("Unesite ID vina: ");
        Integer wineId = inputHandler.getIntInput("");

        boolean exists = customerOrderTransactionService.validateWineExists(wineId);

        if (exists) {
            System.out.println("✅ Vino sa ID " + wineId + " postoji u bazi.");
        } else {
            System.out.println("❌ Vino sa ID " + wineId + " ne postoji u bazi.");
        }
    }

    private void checkCustomerExists() throws SQLException {
        System.out.println("\n=== PROVERA POSTOJANJA KUPCA ===");
        System.out.print("Unesite email kupca: ");
        String email = scanner.nextLine().trim();

        boolean exists = customerOrderTransactionService.validateCustomerEmailExists(email);

        if (exists) {
            System.out.println("✅ Kupac sa email-om " + email + " postoji u bazi.");
        } else {
            System.out.println("❌ Kupac sa email-om " + email + " ne postoji u bazi.");
        }
    }

    private void getCustomerIdByEmail() throws SQLException {
        System.out.println("\n=== PRONALAŽENJE ID KUPCA ===");
        System.out.print("Unesite email kupca: ");
        String email = scanner.nextLine().trim();

        Integer customerId = customerOrderTransactionService.getCustomerIdByEmail(email);

        if (customerId != null) {
            System.out.println("✅ ID kupca sa email-om " + email + " je: " + customerId);
        } else {
            System.out.println("❌ Kupac sa email-om " + email + " nije pronađen.");
        }
    }

    private void displayTransactionResult(TransactionResultDTO result) {
        System.out.println("\n=== REZULTAT TRANSAKCIJE ===");
        
        if (result.isSuccess()) {
            System.out.println("✅ Transakcija uspešno izvršena!");
            System.out.println("Poruka: " + result.getMessage());
            
            if (result.getCustomerId() != null) {
                System.out.println("ID kupca: " + result.getCustomerId());
            }
            
            if (result.getOrderId() != null) {
                System.out.println("ID narudžbe: " + result.getOrderId());
            }
            
            if (result.getBottleId() != null) {
                System.out.println("ID boce: " + result.getBottleId());
            }
        } else {
            System.out.println("❌ Transakcija neuspešna!");
            System.out.println("Greška: " + result.getError());
            if (result.getMessage() != null) {
                System.out.println("Poruka: " + result.getMessage());
            }
        }
    }

    private void displayTransactionHelp() {
        System.out.println("\n=== POMOĆ ZA TRANSAKCIJE ===");
        System.out.println("Kompletna narudžba kupca uključuje:");
        System.out.println("1. Kreiranje novog kupca (Email, Broj telefona)");
        System.out.println("2. Kreiranje nove narudžbe (Datum, Metod plaćanja, Kupac)");
        System.out.println("3. Dodavanje boce vina u narudžbu (Kapacitet, Vino, Narudžba)");
        System.out.println();
        System.out.println("VAŽNO: Email kupca mora biti jedinstven!");
        System.out.println("- Sistem će proveriti da li kupac sa tim email-om već postoji");
        System.out.println("- Ako kupac postoji, transakcija će biti poništena");
        System.out.println("- Koristite opciju 'Proveri da li kupac postoji' za proveru");
        System.out.println();
        System.out.println("Validacija podataka:");
        System.out.println("- Email mora biti u validnom formatu i jedinstven");
        System.out.println("- Broj telefona mora biti u formatu: +1234567890 ili 123-456-7890");
        System.out.println("- Metod plaćanja: cash, card, ili transfer");
        System.out.println("- ID vina mora biti pozitivan broj");
        System.out.println("- Kapacitet boce mora biti između 0 i 10 litara");
        System.out.println();
        System.out.println("Transakcija je atomska - svi koraci se izvršavaju ili se svi poništavaju.");
    }

    public CustomerOrderTransactionService getCustomerOrderTransactionService() {
        return customerOrderTransactionService;
    }
} 