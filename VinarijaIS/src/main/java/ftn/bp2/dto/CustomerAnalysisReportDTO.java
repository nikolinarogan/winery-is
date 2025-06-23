package ftn.bp2.dto;

public class CustomerAnalysisReportDTO {
    private String email;
    private Integer razlicitaVina;
    private String omiljeneSorte;
    private Integer brojNarduzbi;

    // Constructors
    public CustomerAnalysisReportDTO() {}

    public CustomerAnalysisReportDTO(String email, Integer razlicitaVina, String omiljeneSorte, Integer brojNarduzbi) {
        this.email = email;
        this.razlicitaVina = razlicitaVina;
        this.omiljeneSorte = omiljeneSorte;
        this.brojNarduzbi = brojNarduzbi;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getRazlicitaVina() { return razlicitaVina; }
    public void setRazlicitaVina(Integer razlicitaVina) { this.razlicitaVina = razlicitaVina; }

    public String getOmiljeneSorte() { return omiljeneSorte; }
    public void setOmiljeneSorte(String omiljeneSorte) { this.omiljeneSorte = omiljeneSorte; }

    public Integer getBrojNarduzbi() { return brojNarduzbi; }
    public void setBrojNarduzbi(Integer brojNarduzbi) { this.brojNarduzbi = brojNarduzbi; }

    @Override
    public String toString() {
        return "CustomerAnalysisReportDTO{" +
                "email='" + email + '\'' +
                ", razlicitaVina=" + razlicitaVina +
                ", omiljeneSorte='" + omiljeneSorte + '\'' +
                ", brojNarduzbi=" + brojNarduzbi +
                '}';
    }
} 