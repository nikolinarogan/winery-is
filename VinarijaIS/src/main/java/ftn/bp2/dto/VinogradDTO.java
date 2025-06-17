package ftn.bp2.dto;

import java.time.LocalDate;

public class VinogradDTO {
    private Integer idV;
    private String imeV;
    private Float poV;
    private LocalDate datOsn;
    private Float vKap;
    private Integer vinogradIdV;

    // Constructors
    public VinogradDTO() {}

    public VinogradDTO(Integer idV, String imeV, Float poV, LocalDate datOsn, Float vKap, Integer vinogradIdV) {
        this.idV = idV;
        this.imeV = imeV;
        this.poV = poV;
        this.datOsn = datOsn;
        this.vKap = vKap;
        this.vinogradIdV = vinogradIdV;
    }

    // Getters and Setters
    public Integer getIdV() { return idV; }
    public void setIdV(Integer idV) { this.idV = idV; }

    public String getImeV() { return imeV; }
    public void setImeV(String imeV) { this.imeV = imeV; }

    public Float getPoV() { return poV; }
    public void setPoV(Float poV) { this.poV = poV; }

    public LocalDate getDatOsn() { return datOsn; }
    public void setDatOsn(LocalDate datOsn) { this.datOsn = datOsn; }

    public Float getVKap() { return vKap; }
    public void setVKap(Float vKap) { this.vKap = vKap; }

    public Integer getVinogradIdV() { return vinogradIdV; }
    public void setVinogradIdV(Integer vinogradIdV) { this.vinogradIdV = vinogradIdV; }

    @Override
    public String toString() {
        return "VinogradDTO{" +
                "idV=" + idV +
                ", imeV='" + imeV + '\'' +
                ", poV=" + poV +
                ", datOsn=" + datOsn +
                ", vKap=" + vKap +
                ", vinogradIdV=" + vinogradIdV +
                '}';
    }
}