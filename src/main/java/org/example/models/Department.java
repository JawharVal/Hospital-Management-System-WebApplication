package org.example.models;

import lombok.Data;

@Data
public class Department {
    public int id;
    public String name;
    public int numberOfPatients;


    public void incrementPatients() {
        numberOfPatients++;
    }

    public void decrementPatients() {
        numberOfPatients--;
    }
}

