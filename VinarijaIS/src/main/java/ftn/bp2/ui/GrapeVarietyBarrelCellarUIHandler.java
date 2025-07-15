package ftn.bp2.ui;

import ftn.bp2.dto.GrapeVarietyBarrelCellarDTO;
import ftn.bp2.service.GrapeVarietyBarrelCellarService;

import java.sql.SQLException;
import java.util.List;

public class GrapeVarietyBarrelCellarUIHandler {
    private final GrapeVarietyBarrelCellarService service = new GrapeVarietyBarrelCellarService();

    public void displayGrapeVarietyBarrelCellarStats() {
        try {
            List<GrapeVarietyBarrelCellarDTO> stats = service.getGrapeVarietyBarrelCellarStats();
            System.out.println("\n=== ANALIZA BURADI I PODRUMA PO SORTI GROŽĐA ===");
            System.out.printf("%-30s %-15s %-25s %-15s %-20s%n", "Sorta grožđa", "Broj vina", "Ukupni kapacitet buradi", "Broj podruma", "Broj buradi");
            System.out.println("-".repeat(110));
            if (stats.isEmpty()) {
                System.out.println("Nema podataka za prikaz.");
            } else {
                for (GrapeVarietyBarrelCellarDTO dto : stats) {
                    System.out.printf("%-30s %-15d %-25.2f %-15d %-20d%n",
                            dto.getGrapeVariety(),
                            dto.getUniqueWines(),
                            dto.getTotalBarrelCapacity(),
                            dto.getUniqueCellars(),
                            dto.getNumberOfBarrels());
                }
                System.out.println("-".repeat(110));
                System.out.println("Ukupno sorti: " + stats.size());
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving grape variety barrel/cellar stats: " + e.getMessage());
        }
    }
} 