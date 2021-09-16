package com.evan.workoutapp.data;

public class Exercise {
    private String category;
    private String name;

    public Exercise() {
        category = "chest";
        name = "bench press";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
