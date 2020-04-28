package com.example.project3;

public class AlarmDTO {
    private String title;
    private Class<?> page;

    public AlarmDTO(String title, Class<?> page) {
        this.title = title;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getPage() {
        return page;
    }
}