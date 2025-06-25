package ftn.bp2.dto;

public class WineSalesWithGrapeVarietiesDTO {
    private String nazivVina;
    private String sorteGrozdja;
    private Integer ukupnoBoca;
    private Integer uniqueCustomerCount;

    public WineSalesWithGrapeVarietiesDTO() {}

    public WineSalesWithGrapeVarietiesDTO(String nazivVina, String sorteGrozdja, Integer ukupnoBoca, Integer uniqueCustomerCount) {
        this.nazivVina = nazivVina;
        this.sorteGrozdja = sorteGrozdja;
        this.ukupnoBoca = ukupnoBoca;
        this.uniqueCustomerCount = uniqueCustomerCount;
    }

    public String getNazivVina() { return nazivVina; }
    public void setNazivVina(String nazivVina) { this.nazivVina = nazivVina; }

    public String getSorteGrozdja() { return sorteGrozdja; }
    public void setSorteGrozdja(String sorteGrozdja) { this.sorteGrozdja = sorteGrozdja; }

    public Integer getUkupnoBoca() { return ukupnoBoca; }
    public void setUkupnoBoca(Integer ukupnoBoca) { this.ukupnoBoca = ukupnoBoca; }

    public Integer getUniqueCustomerCount() {
        return uniqueCustomerCount;
    }

    public void setUniqueCustomerCount(Integer uniqueCustomerCount) {
        this.uniqueCustomerCount = uniqueCustomerCount;
    }

    @Override
    public String toString() {
        return "WineSalesWithGrapeVarietiesDTO{" +
                "nazivVina='" + nazivVina + '\'' +
                ", sorteGrozdja='" + sorteGrozdja + '\'' +
                ", ukupnoBoca=" + ukupnoBoca +
                ", uniqueCustomerCount=" + uniqueCustomerCount +
                '}';
    }
} 