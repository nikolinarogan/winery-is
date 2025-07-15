package ftn.bp2.dto;

public class GrapeVarietyBarrelCellarDTO {
    private String grapeVariety;
    private int uniqueWines;
    private double totalBarrelCapacity;
    private int uniqueCellars;
    private int numberOfBarrels;

    public GrapeVarietyBarrelCellarDTO() {}

    public GrapeVarietyBarrelCellarDTO(String grapeVariety, int uniqueWines, double totalBarrelCapacity, int uniqueCellars, int numberOfBarrels) {
        this.grapeVariety = grapeVariety;
        this.uniqueWines = uniqueWines;
        this.totalBarrelCapacity = totalBarrelCapacity;
        this.uniqueCellars = uniqueCellars;
        this.numberOfBarrels = numberOfBarrels;
    }

    public String getGrapeVariety() { return grapeVariety; }
    public void setGrapeVariety(String grapeVariety) { this.grapeVariety = grapeVariety; }

    public int getUniqueWines() { return uniqueWines; }
    public void setUniqueWines(int uniqueWines) { this.uniqueWines = uniqueWines; }

    public double getTotalBarrelCapacity() { return totalBarrelCapacity; }
    public void setTotalBarrelCapacity(double totalBarrelCapacity) { this.totalBarrelCapacity = totalBarrelCapacity; }

    public int getUniqueCellars() { return uniqueCellars; }
    public void setUniqueCellars(int uniqueCellars) { this.uniqueCellars = uniqueCellars; }

    public int getNumberOfBarrels() { return numberOfBarrels; }
    public void setNumberOfBarrels(int numberOfBarrels) { this.numberOfBarrels = numberOfBarrels; }

    @Override
    public String toString() {
        return "GrapeVarietyBarrelCellarDTO{" +
                "grapeVariety='" + grapeVariety + '\'' +
                ", uniqueWines=" + uniqueWines +
                ", totalBarrelCapacity=" + totalBarrelCapacity +
                ", uniqueCellars=" + uniqueCellars +
                ", numberOfBarrels=" + numberOfBarrels +
                '}';
    }
} 