package com.example.project3;

public class ChildMoveDTO {
    private String name;
    private Class<?> page;

    public ChildMoveDTO(String name, Class<?> page) {
        this.name = name;
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public Class<?> getPage() {
        return page;
    }
}
