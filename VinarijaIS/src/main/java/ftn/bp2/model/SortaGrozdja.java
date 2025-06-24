package ftn.bp2.model;

import java.util.Objects;

public class SortaGrozdja {

    private Integer idSrt;
    private String nazSrt;
    private String opis;
    private String boja;

    public SortaGrozdja() {}

    public SortaGrozdja(Integer idSrt, String nazSrt) {
        this.idSrt = idSrt;
        this.nazSrt = nazSrt;
    }

    public SortaGrozdja(Integer idSrt, String nazSrt, String opis, String boja) {
        this.idSrt = idSrt;
        this.nazSrt = nazSrt;
        this.opis = opis;
        this.boja = boja;
    }

    public SortaGrozdja(String nazSrt, String opis, String boja) {
        this.nazSrt = nazSrt;
        this.opis = opis;
        this.boja = boja;
    }
    
    public Integer getIdSrt() {
        return idSrt;
    }

    public void setIdSrt(Integer idSrt) {
        this.idSrt = idSrt;
    }

    public String getNazSrt() {
        return nazSrt;
    }

    public void setNazSrt(String nazSrt) {
        this.nazSrt = nazSrt;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }


    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    @Override
    public String toString() {
        return "SortaGrozdja{" +
                "idSrt=" + idSrt +
                ", nazSrt='" + nazSrt + '\'' +
                ", boja='" + boja + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortaGrozdja that = (SortaGrozdja) o;
        return Objects.equals(idSrt, that.idSrt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSrt);
    }
}
