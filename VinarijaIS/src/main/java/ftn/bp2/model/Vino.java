package ftn.bp2.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Vino {

        private Integer idVina;
        private LocalDate god;
        private Float procAlk;
        private String nazVina;
        private List<Boca> boca;

        public Vino() {}

        public Vino(Integer idVina, LocalDate god, Float procAlk, String nazVina) {
            this.idVina = idVina;
            this.god = god;
            this.procAlk = procAlk;
            this.nazVina = nazVina;
        }

        public Vino(LocalDate god, Float procAlk, String nazVina) {
            this.god = god;
            this.procAlk = procAlk;
            this.nazVina = nazVina;
        }

        public Integer getIdVina() {
            return idVina;
        }

        public void setIdVina(Integer idVina) {
            this.idVina = idVina;
        }

        public LocalDate getGod() {
            return god;
        }

        public void setGod(LocalDate god) {
            this.god = god;
        }

        public Float getProcAlk() {
            return procAlk;
        }

        public void setProcAlk(Float procAlk) {
            this.procAlk = procAlk;
        }

        public String getNazVina() {
            return nazVina;
        }

        public void setNazVina(String nazVina) {
            this.nazVina = nazVina;
        }

        public List<Boca> getBoca() {
            return boca;
        }

        public void setBoca(List<Boca> boca) {
            this.boca = boca;
        }

        @Override
        public String toString() {
            return "Vino{" +
                    "idVina=" + idVina +
                    ", god=" + god +
                    ", procAlk=" + procAlk +
                    ", nazVina='" + nazVina + '\'' +
                    ", boca=" + (boca != null ? boca.size() : 0) + " bottles" +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vino vino = (Vino) o;
            return Objects.equals(idVina, vino.idVina);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idVina);
        }

    }

