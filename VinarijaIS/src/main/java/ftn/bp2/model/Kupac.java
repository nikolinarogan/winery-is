package ftn.bp2.model;

import java.util.Objects;

public class Kupac {

    private Integer idKup;
    private String email;
    private String brTel;

    public Kupac() {}

    public Kupac(Integer idKup, String email, String brTel) {
        this.idKup = idKup;
        this.email = email;
        this.brTel = brTel;
    }

    public Kupac(String email, String brTel) {
        this.email = email;
        this.brTel = brTel;
    }

    // Getters and Setters
    public Integer getIdKup() {
        return idKup;
    }

    public void setIdKup(Integer idKup) {
        this.idKup = idKup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrTel() {
        return brTel;
    }

    public void setBrTel(String brTel) {
        this.brTel = brTel;
    }

    @Override
    public String toString() {
        return "Kupac{" +
                "idKup=" + idKup +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kupac kupac = (Kupac) o;
        return Objects.equals(idKup, kupac.idKup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKup);
    }
}
