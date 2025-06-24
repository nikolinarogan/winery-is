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
        System.out.println("1. Gotovina (cash)");
        System.out.println("2. Kartica (card)");
        int paymentChoice = inputHandler.getIntInput("Izaberite metod plaćanja (1-3): ");
        
        String paymentMethod;
        switch (paymentChoice) {
            case 1: paymentMethod = "cash"; break;
            case 2: paymentMethod = "card"; break;
            default: 
                System.out.println("Nevažeći izbor. Koristi se gotovina.");
                paymentMethod = "cash";
        }

        System.out.print("ID vina: ");
        Integer wineId = inputHandler.getIntInput("");

        System.out.print("Kapacitet boce (litri): ");
        Float bottleCapacity = inputHandler.getFloatInput("");

        System.out.println("\nIzvršavam transakciju...");
        TransactionResultDTO result = customerOrderTransactionService.executeCustomerOrderTransaction(
                email, phoneNumber, paymentMethod, wineId, bottleCapacity);

        displayTransactionResult(result);
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
            
            if (result.getBottleId() != null) {
                System.out.println("ID boce: " + result.getBottleId());
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