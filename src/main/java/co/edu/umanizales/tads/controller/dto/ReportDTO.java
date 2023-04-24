package co.edu.umanizales.tads.controller.dto;

public class ReportDTO {
    private String location;
    private String gender;
    private int quantity;

    // Constructor
    public ReportDTO(String location, String gender, int quantity) {
        this.location = location;
        this.gender = gender;
        this.quantity = quantity;
    }

    // Getters y setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

