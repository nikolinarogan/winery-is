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

    public CustomerAnalysisReportService getCustomerAnalysisReportService() {
        return customerAnalysisReportService;
    }
} 