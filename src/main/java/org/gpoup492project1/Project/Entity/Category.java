package org.gpoup492project1.Project.Entity;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
