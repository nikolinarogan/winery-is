package ftn.bp2.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Porudzbina {

    private Integer idNar;
    private LocalDate datNar;
    private String pltMtd;
    private Integer kupacId;
    private List<Boca> boca;

    public Porudzbina() {}

    public Porudzbina(Integer idNar, LocalDate datNar, String pltMtd) {
        this.idNar = idNar;
        this.datNar = datNar;
        this.pltMtd = pltMtd;
    }

    public Porudzbina(LocalDate datNar, String pltMtd) {
        this.datNar = datNar;
        this.pltMtd = pltMtd;
    }

    public Porudzbina(Integer idNar, LocalDate datNar, String pltMtd, Integer kupacId) {
        this.idNar = idNar;
        this.datNar = datNar;
        this.pltMtd = pltMtd;
        this.kupacId = kupacId;
    }

    // Getters and Setters
    public Integer getIdNar() {
        return idNar;
    }

    public void setIdNar(Integer idNar) {
        this.idNar = idNar;
    }

    public LocalDate getDatNar() {
        return datNar;
    }

    public void setDatNar(LocalDate datNar) {
        this.datNar = datNar;
    }

    public String getPltMtd() {
        return pltMtd;
    }

    public void setPltMtd(String pltMtd) {
        this.pltMtd = pltMtd;
    }

    public Integer getKupacId() {
        return kupacId;
    }

    public void setKupacId(Integer kupacId) {
        this.kupacId = kupacId;
    }

    public List<Boca> getBoca() {
        return boca;
    }

    public void setBoca(List<Boca> boca) {
        this.boca = boca;
    }

    public String getPaymentMethodDisplay() {
        if (pltMtd == null) return "Nepoznato";
        switch (pltMtd.toLowerCase()) {
            case "kartica": return "Kartica";
            case "kes": return "Kes";
            default: return pltMtd;
        }
    }

    public int getTotalBottles() {
        return boca != null ? boca.size() : 0;
    }

    public double getTotalVolume() {
        if (boca == null) return 0.0;
        return boca.stream()
                .mapToDouble(bottle -> bottle.getKapBoc() != null ? bottle.getKapBoc() : 0.0)
                .sum();
    }
    @Override
    public String toString() {
        return "Porudzbina{" +
                "idNar=" + idNar +
                ", datNar=" + datNar +
                ", pltMtd='" + getPaymentMethodDisplay() + '\'' +
                ", totalBottles=" + getTotalBottles() +
                ", totalVolume=" + String.format("%.2fL", getTotalVolume()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Porudzbina porudzbina = (Porudzbina) o;
        return Objects.equals(idNar, porudzbina.idNar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNar);
    }

}
