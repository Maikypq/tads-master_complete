package co.edu.umanizales.tads.model;

public class Pet {
    private String identification;
    private String name;
    private String breed;
    private int age;

    public Pet(String identification, String name, String breed, int age) {
        this.identification = identification;
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    // Getters and setters

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}