package ftn.bp2.model;

import java.util.Objects;

public class Boca {

    private Integer serBr;
    private Float kapBoc;
    private Integer vinoId;
    private Integer narudzbaId;
    private Vino vino;
    private Porudzbina porudzbina;


    public Boca() {}

    public Boca(Integer serBr, Float kapBoc, Integer vinoId, Integer narudzbaId) {
        this.serBr = serBr;
        this.kapBoc = kapBoc;
        this.vinoId = vinoId;
        this.narudzbaId = narudzbaId;
    }

    public Boca(Float kapBoc, Integer vinoId, Integer narudzbaId) {
        this.kapBoc = kapBoc;
        this.vinoId = vinoId;
        this.narudzbaId = narudzbaId;
    }

    public Boca(Integer serBr, Float kapBoc, Integer vinoId, Integer narudzbaId, Vino vino, Porudzbina porudzbina) {
        this.serBr = serBr;
        this.kapBoc = kapBoc;
        this.vinoId = vinoId;
        this.narudzbaId = narudzbaId;
        this.vino = vino;
        this.porudzbina = porudzbina;
    }

    // Getters and Setters
    public Integer getSerBr() {
        return serBr;
    }

    public void setSerBr(Integer serBr) {
        this.serBr = serBr;
    }

    public Float getKapBoc() {
        return kapBoc;
    }

    public void setKapBoc(Float kapBoc) {
        this.kapBoc = kapBoc;
    }

    public Integer getVinoId() {
        return vinoId;
    }

    public void setVinoId(Integer vinoId) {
        this.vinoId = vinoId;
    }

    public Integer getNarudzbaId() {
        return narudzbaId;
    }

    public void setNarudzbaId(Integer narudzbaId) {
        this.narudzbaId = narudzbaId;
    }

    public Vino getVino() {
        return vino;
    }

    public void setVino(Vino vino) {
        this.vino = vino;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }


    public String getWineName() {
        return vino != null ? vino.getNazVina() : null;
    }
    public String getSizeCategory() {
        if (kapBoc == null) return "Unknown";
        if (kapBoc < 0.5f) return "Small";
        if (kapBoc < 0.75f) return "Medium";
        if (kapBoc < 1.0f) return "Standard";
        if (kapBoc < 1.5f) return "Large";
        return "Magnum";
    }
    @Override
    public String toString() {
        return "Boca{" +
                "serBr=" + serBr +
                ", kapBoc=" + kapBoc + "L" +
                ", vinoId=" + vinoId +
                ", narudzbaId=" + narudzbaId +
                ", wineName='" + getWineName() + '\'' +
                ", sizeCategory='" + getSizeCategory() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boca boca = (Boca) o;
        return Objects.equals(serBr, boca.serBr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serBr);
    }

}
