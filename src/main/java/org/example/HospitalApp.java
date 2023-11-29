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
                case 1:
                    // Add Department
                    String departmentName;
                    do {
                        System.out.print("Enter the department name: ");
                        departmentName = scanner.nextLine();
                        if (departmentName.contains(" ")) {
                            System.out.println("Invalid name. Department name should be one word without spaces.");
                        }
                    } while (departmentName.contains(" "));

                    Department newDepartment = new Department();
                    newDepartment.setName(departmentName);
                    try {
                        Department existingDepartment = departmentRepository.getDepartmentByName(departmentName);
                        if (existingDepartment == null) {
                            departmentRepository.addDepartment(newDepartment);
                            System.out.println("Department added: " + newDepartment.getName());
                        } else {
                            System.out.println("A department with this name already exists.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;



                case 3:
                    // Delete Department
                    System.out.print("Enter the department name to delete: ");
                    String departmentNameToDelete = scanner.next();
                    try {
                        Department departmentToDelete = departmentRepository.getDepartmentByName(departmentNameToDelete);
                        if (departmentToDelete != null) {
                            departmentRepository.deleteDepartment(departmentToDelete.getId());
                            System.out.println("Department deleted: " + departmentNameToDelete);
                        } else {
                            System.out.println("Department not found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    // Edit Department
                    System.out.print("Enter the department name to update: ");
                    String departmentNameToUpdate = scanner.next();
                    try {
                        Department departmentToUpdate = departmentRepository.getDepartmentByName(departmentNameToUpdate);
                        if (departmentToUpdate != null) {
                            System.out.print("Enter the new department name: ");
                            String newDepartmentName = scanner.next();
                            departmentToUpdate.setName(newDepartmentName);
                            departmentRepository.updateDepartment(departmentToUpdate);
                            System.out.println("Department updated: " + newDepartmentName);
                        } else {
                            System.out.println("Department not found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

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

                case 2:
                    System.out.print("Enter the department name: ");
                    String a = scanner.next();
                    scanner.nextLine();
                    try {
                        Department department = departmentRepository.getDepartmentByName(a);
                        if (department != null) {
                            // Add Patient
                            String patientName;
                            do {
                                System.out.print("Enter the patient's name: ");
                                patientName = scanner.nextLine();
                                if (!patientName.matches("[a-zA-Z\\s]+")) {
                                    System.out.println("Invalid name. Please enter a valid name.");
                                }
                            } while (!patientName.matches("[a-zA-Z\\s]+"));


                            int patientAge;
                            do {
                                System.out.print("Enter the patient's age: ");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("That's not a valid age! Please enter an integer.");
                                    scanner.next(); // discard the invalid input
                                }
                                patientAge = scanner.nextInt();
                                if (patientAge < 0 || patientAge > 120) {
                                    System.out.println("Invalid age. Please enter a reasonable age.");
                                }
                            } while (patientAge < 0 || patientAge > 120);

                            String patientGender;
                            do {
                                System.out.print("Enter the patient's gender: ");
                                patientGender = scanner.next();
                                if (!patientGender.equalsIgnoreCase("male") && !patientGender.equalsIgnoreCase("female")) {
                                    System.out.println("Invalid gender. Please enter either 'male' or 'female'.");
                                }
                            } while (!patientGender.equalsIgnoreCase("male") && !patientGender.equalsIgnoreCase("female"));

                            Patient newPatient = new Patient();
                            newPatient.setFullName(patientName);
                            newPatient.setAge(patientAge);
                            newPatient.setGender(patientGender);
                            newPatient.setDepartment(department);
                            patientRepository.addPatient(newPatient);
                            System.out.println("Patient added: " + newPatient.getFullName());
                        } else {
                            System.out.println("Department not found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;


                case 4:
                    // Delete Patient
                    System.out.print("Enter the patient's name to delete: ");
                    String patientNameToDelete = scanner.next();
                    try {
                        List<Patient> patientsToDelete = patientRepository.getPatientsByName(patientNameToDelete);
                        if (patientsToDelete.isEmpty()) {
                            System.out.println("Patient not found.");
                        } else if (patientsToDelete.size() == 1) {
                            patientRepository.deletePatient(patientsToDelete.get(0).getId());
                            System.out.println("Patient deleted: " + patientNameToDelete);
                        } else {
                            System.out.println("Multiple patients found with the same name. Please specify the department:");
                            for (int i = 0; i < patientsToDelete.size(); i++) {
                                System.out.println((i + 1) + ". " + patientsToDelete.get(i).getDepartment().getName());
                            }
                            int departmentChoice = scanner.nextInt();
                            patientRepository.deletePatient(patientsToDelete.get(departmentChoice - 1).getId());
                            System.out.println("Patient deleted: " + patientNameToDelete);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;


                case 6:
                    // Edit Patient
                    System.out.print("Enter the patient's name to update: ");
                    String patientNameToUpdate = scanner.next();
                    try {
                        Patient patientToUpdate = patientRepository.getPatientByName(patientNameToUpdate);
                        if (patientToUpdate != null) {
                            String newFullName;
                            do {
                                System.out.print("Enter the new name: ");
                                newFullName = scanner.next();
                                if (!newFullName.matches("[a-zA-Z\\s]+")) {
                                    System.out.println("Invalid name. Please enter a valid name.");
                                }
                            } while (!newFullName.matches("[a-zA-Z\\s]+"));

                            int newAge;
                            do {
                                System.out.print("Enter the new age: ");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("That's not a valid age! Please enter an integer.");
                                    scanner.next(); // discard the invalid input
                                }
                                newAge = scanner.nextInt();
                                scanner.nextLine(); // consume leftover newline
                                if (newAge < 0 || newAge > 120) {
                                    System.out.println("Invalid age. Please enter a reasonable age.");
                                }
                            } while (newAge < 0 || newAge > 120);

                            String newGender;
                            do {
                                System.out.print("Enter the new gender: ");
                                newGender = scanner.next();
                                scanner.nextLine(); // consume leftover newline
                                if (!newGender.equalsIgnoreCase("male") && !newGender.equalsIgnoreCase("female")) {
                                    System.out.println("Invalid gender. Please enter either 'male' or 'female'.");
                                }
                            } while (!newGender.equalsIgnoreCase("male") && !newGender.equalsIgnoreCase("female"));

                            patientToUpdate.setFullName(newFullName);
                            patientToUpdate.setAge(newAge);
                            patientToUpdate.setGender(newGender);
                            patientRepository.updatePatient(patientToUpdate);
                            System.out.println("Patient updated: " + newFullName);
                        } else {
                            System.out.println("Patient not found.");
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