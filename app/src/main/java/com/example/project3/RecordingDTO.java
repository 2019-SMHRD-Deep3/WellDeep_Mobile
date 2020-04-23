package com.example.project3;

public class RecordingDTO {
    private String title;
    private Class<?> page;

    public RecordingDTO(String title, Class<?> page) {
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