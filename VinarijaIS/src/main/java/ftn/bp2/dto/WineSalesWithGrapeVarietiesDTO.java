package ftn.bp2.dto;

public class WineSalesWithGrapeVarietiesDTO {
    private String nazivVina;
    private String sorteGrozdja;
    private Integer ukupnoBoca;
    private Double ukupanPrihod;

    public WineSalesWithGrapeVarietiesDTO() {}

    public WineSalesWithGrapeVarietiesDTO(String nazivVina, String sorteGrozdja, Integer ukupnoBoca, Double ukupanPrihod) {
        this.nazivVina = nazivVina;
        this.sorteGrozdja = sorteGrozdja;
        this.ukupnoBoca = ukupnoBoca;
        this.ukupanPrihod = ukupanPrihod;
    }

    public String getNazivVina() { return nazivVina; }
    public void setNazivVina(String nazivVina) { this.nazivVina = nazivVina; }

    public String getSorteGrozdja() { return sorteGrozdja; }
    public void setSorteGrozdja(String sorteGrozdja) { this.sorteGrozdja = sorteGrozdja; }

    public Integer getUkupnoBoca() { return ukupnoBoca; }
    public void setUkupnoBoca(Integer ukupnoBoca) { this.ukupnoBoca = ukupnoBoca; }

    public Double getUkupanPrihod() { return ukupanPrihod; }
    public void setUkupanPrihod(Double ukupanPrihod) { this.ukupanPrihod = ukupanPrihod; }

    @Override
    public String toString() {
        return "WineSalesWithGrapeVarietiesDTO{" +
                "nazivVina='" + nazivVina + '\'' +
                ", sorteGrozdja='" + sorteGrozdja + '\'' +
                ", ukupnoBoca=" + ukupnoBoca +
                ", ukupanPrihod=" + ukupanPrihod +
                '}';
    }
} 