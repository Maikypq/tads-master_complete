package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Kid {
    private int identification;
    private String name;
    private byte age;
    private char gender;
    private String location;

    public Kid(int identification, String name, int age, String gender, String location) {
    }
}
