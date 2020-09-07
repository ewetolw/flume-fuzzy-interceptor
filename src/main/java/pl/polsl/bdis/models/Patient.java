package pl.polsl.bdis.models;


import java.io.Serializable;

public class Patient implements Serializable {

    Integer id;
    Double age;
    Double temperature;
    String gender;

    public Patient(Integer id, Double age, Double temperature, String gender) {
        this.id = id;
        this.age = age;
        this.temperature = temperature;
        this.gender = gender;
    }

    public Patient() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "obiekt Patient{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", temperature=" + temperature +
                ", gender='" + gender + '\'' +
                '}';
    }
}
