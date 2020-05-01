package com.example.project3;

public class ChildDTO {

    private String c_name;
    private String c_age;
    private String c_sex;
    private String c_photo;
    private String c_number;

    public ChildDTO(String c_name, String c_age, String c_sex, String c_photo, String c_number) {
        this.c_name = c_name;
        this.c_age = c_age;
        this.c_sex = c_sex;
        this.c_photo = c_photo;
        this.c_number = c_number;
    }

    public String getC_name() {
        return c_name;
    }

    public String getC_age() {
        return c_age;
    }

    public String getC_sex() {
        return c_sex;
    }

    public String getC_photo() {
        return c_photo;
    }

    public String getC_number() {
        return c_number;
    }
}