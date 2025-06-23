package ftn.bp2.dto;

public class GrapeVarietyWineCountDTO {
    private String grapeVariety;
    private Integer numberOfWines;

    public GrapeVarietyWineCountDTO() {}

    public GrapeVarietyWineCountDTO(String grapeVariety, Integer numberOfWines) {
        this.grapeVariety = grapeVariety;
        this.numberOfWines = numberOfWines;
    }


    public String getGrapeVariety() { return grapeVariety; }
    public void setGrapeVariety(String grapeVariety) { this.grapeVariety = grapeVariety; }

    public Integer getNumberOfWines() { return numberOfWines; }
    public void setNumberOfWines(Integer numberOfWines) { this.numberOfWines = numberOfWines; }

    @Override
    public String toString() {
        return "GrapeVarietyWineCountDTO{" +
                "grapeVariety='" + grapeVariety + '\'' +
                ", numberOfWines=" + numberOfWines +
                '}';
    }
} 