package org.example.models;


import lombok.Data;

@Data
public class Patient {
    public int id;
    public String fullName;
    public int age;
    public String gender;
    public Department department;


}

