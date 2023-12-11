package org.example;

import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.models.Department;
import org.example.models.Patient;
import org.example.repositories.DepartmentRepository;
import org.example.repositories.PatientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Component
public class HospitalApp {

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private PatientRepository patientRepository;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Hospital Management System Menu:");
            System.out.println("1. Add Department");
            System.out.println("2. Add Patient");
            System.out.println("3. Remove Department");
            System.out.println("4. Remove Patient");
            System.out.println("5. Edit Department");
            System.out.println("6. Edit Patient");
            System.out.println("7. Display Department Information");
            System.out.println("8. Display Patient Information");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");


            do {
                System.out.print("Enter your choice: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("That's not a valid choice! Please enter an integer.");
                    scanner.next(); // discard the invalid input
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // consume leftover newline
            } while (choice < 0 || choice > 8);

            switch (choice) {

                case 7:
                    // Display Department Information
                    try {
                        List<Department> departments = departmentRepository.getAllDepartments();
                        if (!departments.isEmpty()) {
                            System.out.println("List of Departments:");
                            for (Department department : departments) {
                                System.out.println("Department Name: " + department.getName());
                                System.out.println("Number of Patients: " + department.getNumberOfPatients());
                                System.out.println();
                            }
                        } else {
                            System.out.println("No departments found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 8:
                    // Display Patient Information
                    try {
                        List<Patient> patients = patientRepository.getAllPatients();
                        if (!patients.isEmpty()) {
                            System.out.println("List of Patients:");
                            for (Patient patient : patients) {
                                System.out.println("Name: " + patient.getFullName());
                                System.out.println("Age: " + patient.getAge());
                                System.out.println("Gender: " + patient.getGender());
                                // Display the department name
                                if (patient.getDepartment() != null) {
                                    System.out.println("Department: " + patient.getDepartment().getName());
                                } else {
                                    System.out.println("Department: Not assigned");
                                }
                                System.out.println();
                            }
                        } else {
                            System.out.println("No patients found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        } while (choice != 0);
    }
}