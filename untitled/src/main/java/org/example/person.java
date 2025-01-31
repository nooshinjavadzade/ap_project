package org.example;

public class person {
    private int age;
    private int height;
    private int weight;
    private String gender;
    private String bodyshape;
    private String monasebat;

    public person(int age, int height, int weight, String gender, String bodyshape, String monasebat) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.bodyshape = bodyshape;
        this.monasebat = monasebat;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getBodyshape() {
        return bodyshape;
    }
    public void setBodyshape(String bodyshape) {
        this.bodyshape = bodyshape;
    }
    public String getMonasebat() {
        return monasebat;
    }
    public void setMonasebat(String monasebat) {
        this.monasebat = monasebat;
    }
    public double BMI() {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
    public String body (int bmi){
        if (bmi < 16.5){
            return "Severely underweight";
        }
        else if (bmi < 18.5){
            return "Underweight";
        }
        else if (bmi < 24.5){
            return "normal";
        }
        else if (bmi < 29.5) {
            return "overweight";
        }
        else if (bmi < 34.5) {
            return "First degree obesity";
        }
        else if (bmi < 39.5) {
            return "Second degree obesity";
        }
        else{
            return "Third grade obesity";
        }
    }
}