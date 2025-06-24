package ftn.bp2.dto;

public class GrapeVarietyWineCountDTO {
    private String grapeVariety;
    private Integer numberOfWines;
    private Integer oldestWineYear;

    public GrapeVarietyWineCountDTO() {}

    public GrapeVarietyWineCountDTO(String grapeVariety, Integer numberOfWines, Integer oldestWineYear) {
        this.grapeVariety = grapeVariety;
        this.numberOfWines = numberOfWines;
        this.oldestWineYear = oldestWineYear;
    }


    public String getGrapeVariety() { return grapeVariety; }
    public void setGrapeVariety(String grapeVariety) { this.grapeVariety = grapeVariety; }

    public Integer getNumberOfWines() { return numberOfWines; }
    public void setNumberOfWines(Integer numberOfWines) { this.numberOfWines = numberOfWines; }

    public Integer getOldestWineYear() {
        return oldestWineYear;
    }

    public void setOldestWineYear(Integer oldestWineYear) {
        this.oldestWineYear = oldestWineYear;
    }

    @Override
    public String toString() {
        return "GrapeVarietyWineCountDTO{" +
                "grapeVariety='" + grapeVariety + '\'' +
                ", numberOfWines=" + numberOfWines +
                ", oldestWineYear=" + oldestWineYear +
                '}';
    }
} 